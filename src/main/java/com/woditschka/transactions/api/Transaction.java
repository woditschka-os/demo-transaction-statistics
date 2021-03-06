package com.woditschka.transactions.api;

/**
 * Rest API transaction model, serialized from Json by Spring MVC
 */
public class Transaction {
  private double amount;
  private long timestamp;

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "amount=" + amount +
        ", timestamp=" + timestamp +
        '}';
  }
}
