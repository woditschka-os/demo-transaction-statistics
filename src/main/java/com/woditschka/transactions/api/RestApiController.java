package com.woditschka.transactions.api;

import com.woditschka.transactions.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * API entry points, translates requests and responsed and delegates to service layer
 */
@RestController
public class RestApiController {
  private final TransactionsService transactionsService;

  @Autowired
  public RestApiController(TransactionsService transactionsService) {
    this.transactionsService = transactionsService;
  }

  @PostMapping("/transactions")
  public ResponseEntity transactions(@RequestBody Transaction transaction) {
    boolean accepted = transactionsService.acceptTransaction(transaction.getTimestamp(), transaction.getAmount());
    return new ResponseEntity(accepted ? CREATED : NO_CONTENT);
  }

  @GetMapping("/statistics")
  public Statistics statistics() {
    return new Statistics(transactionsService.cachedTransactionStatistics());
  }
}
