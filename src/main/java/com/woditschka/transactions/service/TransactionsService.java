package com.woditschka.transactions.service;

import com.woditschka.transactions.domain.Transaction;
import com.woditschka.transactions.domain.TransactionStatistics;
import com.woditschka.transactions.domain.TransactionStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

import static java.time.Instant.now;


/**
 * The transaction service manages access to the transaction stream and the transaction statistics.
 * To allow a time complexity of O(1) to access the statistics calculation and retrieval are decoupled.
 * <p>
 * --store-transaction O(log n)--> transaction stream --periodic statistics-and-cleanup (O(n)--> cache --read O(1)-->
 */
@Service
public class TransactionsService {
  private final TransactionStream transactionStream;
  private final AtomicReference<TransactionStatistics> transactionStatisticsCache = new AtomicReference<>();

  @Autowired
  public TransactionsService(
      @Value("${transaction.stream-window-size-milliseconds}") int windowSizeMilliseconds,
      @Value("${transaction.stream-initial-capacity}") int initialCapacity) {
    transactionStream = new TransactionStream(windowSizeMilliseconds, initialCapacity);
  }

  /**
   * Post a transaction to the stream - time complexity O(log n)
   */
  public boolean acceptTransaction(long timestamp, double amount) {
    return transactionStream.accept(now().toEpochMilli(), new Transaction(timestamp, amount));
  }

  /**
   * Read cached transaction statistics - time complexity O(1)
   */
  public TransactionStatistics cachedTransactionStatistics() {
    return transactionStatisticsCache.get();
  }

  /**
   * Periodic build transaction statisctics, store in cache and cleanup
   */
  @Scheduled(fixedDelay = 10)
  public void buildTransactionStatistics() {
    transactionStatisticsCache.set(transactionStream.buildStatisticsAndCleanup(now().toEpochMilli()));
  }
}
