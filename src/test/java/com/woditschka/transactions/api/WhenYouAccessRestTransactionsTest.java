package com.woditschka.transactions.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.woditschka.transactions.api.RestTestHelper.*;
import static java.time.Instant.now;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WhenYouAccessRestTransactionsTest {

  @Test
  public void requestsWithinTimeframeSouldBeAccepted() {
    givenRestEndpoint()
        .with().body(transactionJson(now(), SOME_AMOUNT)).contentType("application/json")
        .when().post("/transactions")
        .then().statusCode(201);
  }

  @Test
  public void requestsBeforeTimeframeShouldBeDenied() {
    givenRestEndpoint()
        .with().body(transactionJson(now().minusSeconds(61), SOME_AMOUNT)).contentType("application/json")
        .when().post("/transactions")
        .then().statusCode(204);
  }

  @Test
  public void requestsAfterTimeframeShouldBeDenied() {
    givenRestEndpoint()
        .with().body(transactionJson(now().plusSeconds(1), SOME_AMOUNT)).contentType("application/json")
        .when().post("/transactions")
        .then().statusCode(204);
  }
}
