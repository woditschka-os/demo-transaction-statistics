package com.woditschka.transactions.api;

import com.woditschka.transactions.domain.TransactionStatistics;

/**
 * Rest API staistics model, serialized to Json by Spring MVC
 */
public class Statistics {
  private double sum;
  private double avg;
  private double max;
  private double min;
  private long count;

  public Statistics(TransactionStatistics transactionStatistics) {
    this.sum = transactionStatistics.getSum();
    this.avg = transactionStatistics.getAvg();
    this.max = transactionStatistics.getMax();
    this.min = transactionStatistics.getMin();
    this.count = transactionStatistics.getCount();
  }

  public double getSum() {
    return sum;
  }

  public double getAvg() {
    return avg;
  }

  public double getMax() {
    return max;
  }

  public double getMin() {
    return min;
  }

  public long getCount() {
    return count;
  }

  @Override
  public String toString() {
    return "Statistics{" +
        "sum=" + sum +
        ", avg=" + avg +
        ", max=" + max +
        ", min=" + min +
        ", count=" + count +
        '}';
  }
}
