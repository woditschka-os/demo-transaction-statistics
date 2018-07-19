package com.woditschka.transactions.domain;

class TransactionStreamTestHelper {
  static final int SOME_INITIAL_CAPACITY = 1000;
  static final double SOME_AMOUNT = 20.0d;
  static final double SOME_LOWER_AMOUNT = 10.0d;

  static final long SOME_TIMESTAMP = 1000000L;
  static final long SOME_WINDOW_SIZE_MILLISECONDS = 60000L;
  static final long SOME_WINDOW_LOWER_BOUND = SOME_TIMESTAMP - SOME_WINDOW_SIZE_MILLISECONDS;

  static TransactionStream someTransactionStream() {
    return new TransactionStream(SOME_WINDOW_SIZE_MILLISECONDS, SOME_INITIAL_CAPACITY);
  }
}
