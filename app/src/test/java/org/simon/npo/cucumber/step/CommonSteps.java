package org.simon.npo.cucumber.step;

import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.simon.npo.cucumber.AbstractStepsDefinitions;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CommonSteps extends AbstractStepsDefinitions {

  @Then("тогда получен код ответа {int}")
  public void startIndirectActivity(Integer responseCode) {
    final ResponseEntity apiResponse = (ResponseEntity) getContext().getApiResponse();
    Assertions.assertEquals(HttpStatusCode.valueOf(responseCode), apiResponse.getStatusCode());
  }
}
