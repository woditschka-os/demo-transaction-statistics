package com.woditschka.transactions.domain;

import org.junit.Test;

import static com.woditschka.transactions.domain.TransactionStreamTestHelper.*;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WhenYouQueryStatisticsFromATransactionStreamTest {

  @Test
  public void anEmptyStreamShouldHaveEmptyStatistics() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(0, 0, 0, NEGATIVE_INFINITY, POSITIVE_INFINITY)));
  }

  @Test
  public void aSingeTransactionShouldProcuceValidStatistics() {
    TransactionStream transactionStream = someTransactionStream();
    transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_AMOUNT));

    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(1, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));
  }

  @Test
  public void twoTransactionsShouldProcuceValidStatistics() {
    TransactionStream transactionStream = someTransactionStream();
    transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_LOWER_AMOUNT));
    transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_AMOUNT));

    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(
        new TransactionStatistics(2, SOME_AMOUNT + SOME_LOWER_AMOUNT, (SOME_AMOUNT + SOME_LOWER_AMOUNT) / 2, SOME_AMOUNT, SOME_LOWER_AMOUNT)));
  }

  @Test
  public void expiredTransactionsShouldBeRemoved() {
    TransactionStream transactionStream = someTransactionStream();

    // feed transaction at lower bound of window
    transactionStream.accept(SOME_WINDOW_LOWER_BOUND, new Transaction(SOME_WINDOW_LOWER_BOUND, SOME_AMOUNT));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(1, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));

    // add another transaction on upper bound
    transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_LOWER_AMOUNT));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(
        new TransactionStatistics(2, SOME_AMOUNT + SOME_LOWER_AMOUNT, (SOME_AMOUNT + SOME_LOWER_AMOUNT) / 2, SOME_AMOUNT, SOME_LOWER_AMOUNT)));

    // query buildStatistics which removes expired transactions
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP + 1), equalTo(new TransactionStatistics(1, SOME_LOWER_AMOUNT, SOME_LOWER_AMOUNT, SOME_LOWER_AMOUNT, SOME_LOWER_AMOUNT)));
  }
}
