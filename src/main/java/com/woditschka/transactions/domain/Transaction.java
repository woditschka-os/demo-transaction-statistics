package com.woditschka.transactions.domain;

/**
 * Immutable transaction stored within the transaction stream
 */
public class Transaction {
  private final long timestamp;
  private final double amount;

  public Transaction(long timestamp, double amount) {
    this.timestamp = timestamp;
    this.amount = amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "timestamp=" + timestamp +
        ", amount=" + amount +
        '}';
  }
}
