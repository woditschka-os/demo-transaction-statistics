package com.woditschka.transactions.domain;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.PriorityBlockingQueue;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.summarizingDouble;

/**
 * Transaction stream processor capable of managing a configurable sliding time window.
 * The stream is stored within a threadsafe min-heap.
 * <p>
 * The min-heao allows to add transactions with a time complexity of O(log n), staistics calculation and cleanup
 * of expired transactions with a time complexity of O(n).
 */
public class TransactionStream {
  private final long windowSizeMilliseconds;
  private final PriorityBlockingQueue<Transaction> transactions;

  public TransactionStream(long windowSizeMilliseconds, int initialCapacity) {
    this.windowSizeMilliseconds = windowSizeMilliseconds;
    this.transactions = new PriorityBlockingQueue<>(initialCapacity, comparingLong(Transaction::getTimestamp));
  }

  /**
   * Post a transaction to the stream - time complexity O(log n)
   *
   * @param now         cureent timestamp (epoch)
   * @param transaction transaction to store
   * @return true when accepted
   */
  public boolean accept(long now, Transaction transaction) {
    boolean accept = isWithinWindow(transaction, now);
    if (accept) {
      transactions.offer(transaction);
    }
    return accept;
  }

  /**
   * Build statistics and remove expired transactions - time complexity O(n).
   *
   * @param now cureent timestamp (epoch)
   * @return transaction statistics within the sliding time window
   */
  public TransactionStatistics buildStatisticsAndCleanup(long now) {
    removeExpiredTransctions(now);
    DoubleSummaryStatistics statistics = buildStatistics();

    return new TransactionStatistics(statistics);
  }

  private DoubleSummaryStatistics buildStatistics() {
    return transactions.stream()
        .collect(summarizingDouble(Transaction::getAmount));
  }

  private void removeExpiredTransctions(long now) {
    while (!transactions.isEmpty() && !isWithinWindow(transactions.peek(), now)) {
      transactions.poll();
    }
  }

  private boolean isWithinWindow(Transaction transaction, long now) {
    return transaction != null
        && transaction.getTimestamp() >= now - windowSizeMilliseconds
        && transaction.getTimestamp() <= now;
  }
}
