package com.woditschka.transactions.domain;

import org.junit.Test;

import static com.woditschka.transactions.domain.TransactionStreamTestHelper.*;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WhenYouPostTransactionsToATransactionStreamTest {

  @Test
  public void timestampsBeforeWindowLowerBoundShuoldNotBeAccepted() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP - SOME_WINDOW_SIZE_MILLISECONDS - 1, SOME_AMOUNT)), is(false));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(0, 0, 0, NEGATIVE_INFINITY, POSITIVE_INFINITY)));
  }

  @Test
  public void timestampsAtWindowLowerBoundShuoldBeAccepted() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_WINDOW_LOWER_BOUND, SOME_AMOUNT)), is(true));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(1, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));
  }

  @Test
  public void timestampWithinWindowShuoldBeAccepted() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP - SOME_WINDOW_SIZE_MILLISECONDS + 1, SOME_AMOUNT)), is(true));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(1, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));
  }

  @Test
  public void timestampsAtWindowUpperBoundShuoldBeAccepted() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_AMOUNT)), is(true));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(1, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));
  }

  @Test
  public void timestampsInTheFutureShuoldNotBeAccepted() {
    TransactionStream transactionStream = someTransactionStream();

    assertThat(transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP + 1, SOME_AMOUNT)), is(false));
    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP), equalTo(new TransactionStatistics(0, 0, 0, NEGATIVE_INFINITY, POSITIVE_INFINITY)));
  }

}
