package com.woditschka.transactions.api;

import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;

import static io.restassured.RestAssured.given;

public class RestTestHelper {
  static final double SOME_AMOUNT = 10.1;

  static RequestSpecification givenRestEndpoint() {
    return given().baseUri("http://localhost").port(8080);
  }

  static String transactionJson(Instant timestamp, double amount) {
    try {
      return new JSONObject().put("amount", amount).put("timestamp", timestamp.toEpochMilli()).toString();
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
}
