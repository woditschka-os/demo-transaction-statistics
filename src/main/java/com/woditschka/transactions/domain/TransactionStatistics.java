package com.woditschka.transactions.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.DoubleSummaryStatistics;

/**
 * Immutable transaction staistics calculated from transactions stored within the transaction stream
 */
public class TransactionStatistics {
  private final long count;
  private final double sum;
  private final double avg;
  private final double max;
  private final double min;

  public TransactionStatistics(long count, double sum, double avg, double max, double min) {
    this.count = count;
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
  }

  public TransactionStatistics(DoubleSummaryStatistics statistics) {
    this.count = statistics.getCount();
    this.sum = statistics.getSum();
    this.avg = statistics.getAverage();
    this.max = statistics.getMax();
    this.min = statistics.getMin();
  }

  public long getCount() {
    return count;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransactionStatistics that = (TransactionStatistics) o;
    return new EqualsBuilder()
        .append(count, that.count)
        .append(sum, that.sum)
        .append(avg, that.avg)
        .append(max, that.max)
        .append(min, that.min)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(count)
        .append(sum)
        .append(avg)
        .append(max)
        .append(min)
        .toHashCode();
  }

  @Override
  public String toString() {
    return "TransactionStatistics{" +
        "count=" + count +
        ", sum=" + sum +
        ", avg=" + avg +
        ", max=" + max +
        ", min=" + min +
        '}';
  }
}
