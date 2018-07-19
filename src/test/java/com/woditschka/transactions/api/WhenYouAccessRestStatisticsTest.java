package com.woditschka.transactions.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.woditschka.transactions.api.RestTestHelper.givenRestEndpoint;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WhenYouAccessRestStatisticsTest {

  @Test
  public void statisticsframeSouldBeAccepted() {
    givenRestEndpoint()
        .when().get("/statistics")
        .then().statusCode(200);
  }
}
