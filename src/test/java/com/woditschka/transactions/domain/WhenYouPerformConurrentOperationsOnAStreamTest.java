package com.woditschka.transactions.domain;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.woditschka.transactions.domain.TransactionStreamTestHelper.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WhenYouPerformConurrentOperationsOnAStreamTest {
  private static final int TIMEOUT_MILLISECONDS = 1000;

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling1transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(1);
  }

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling10transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(10);
  }

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling100transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(100);
  }

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling1000transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(1000);
  }

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling10000transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(10000);
  }

  @Test(timeout = TIMEOUT_MILLISECONDS)
  public void handling100000transactionShouldSucceed() throws InterruptedException {
    verifyParallelTransactionStatistics(100000);
  }

  private void verifyParallelTransactionStatistics(int transactions) throws InterruptedException {
    TransactionStream transactionStream = someTransactionStream();

    executeConcurrentTasks(transactions, () -> transactionStream.accept(SOME_TIMESTAMP, new Transaction(SOME_TIMESTAMP, SOME_AMOUNT)));

    assertThat(transactionStream.buildStatisticsAndCleanup(SOME_TIMESTAMP),
        equalTo(new TransactionStatistics(transactions, SOME_AMOUNT * transactions, SOME_AMOUNT, SOME_AMOUNT, SOME_AMOUNT)));
  }

  private void executeConcurrentTasks(int transactions, Runnable task) throws InterruptedException {
    ExecutorService es = Executors.newFixedThreadPool(4);

    for (int count = 0; count < transactions; count++) {
      es.execute(task);
    }
    es.shutdown();
    es.awaitTermination(TIMEOUT_MILLISECONDS, MILLISECONDS);
  }
}
