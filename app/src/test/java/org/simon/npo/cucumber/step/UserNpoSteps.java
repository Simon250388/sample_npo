package org.simon.npo.cucumber.step;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.simon.npo.NpoClient;
import org.simon.npo.cucumber.AbstractStepsDefinitions;
import org.simon.npo.dto.UserNpoStartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class UserNpoSteps extends AbstractStepsDefinitions {
  @Autowired NpoClient client;

  @When("пользователь {string} начинает нпо {string}")
  public void startIndirectActivity(String userName, String npoType) {
    var response =
        client.startNpoActivity(
            UserNpoStartRequest.builder()
                .withActivity(npoType)
                .withUserNames(Set.of(userName))
                .build());
    verifyResponse(response);
  }

  @When("пользователю {string} назначают нпо {string} с текущего момента до {cucumberDate}")
  public void whenAssignerStartUserActivityFromNowToPlannedMoment(
      String userName, String npoType, LocalDateTime plannedEndTime) {
    var response =
        client.startNpoActivity(
            UserNpoStartRequest.builder()
                .withActivity(npoType)
                .withUserNames(Set.of(userName))
                .withPlannedEndTime(OffsetDateTime.of(plannedEndTime, ZoneOffset.UTC))
                .build());
    verifyResponse(response);
  }

  @When("пользователю {string} назначают нпо {string} с {cucumberDate} до {cucumberDate}")
  public void whenAssignerStartUserActivityToPlannedMoment(
      String userName, String npoType, LocalDateTime startTime, LocalDateTime plannedEndTime) {
    var response =
        client.startNpoActivity(
            UserNpoStartRequest.builder()
                .withActivity(npoType)
                .withUserNames(Set.of(userName))
                .withPlannedEndTime(OffsetDateTime.of(plannedEndTime, ZoneOffset.UTC))
                .withStartTime(OffsetDateTime.of(startTime, ZoneOffset.UTC))
                .build());
    verifyResponse(response);
  }

  @Then("у пользователя {string} есть действующее нпо {string}")
  public void verifyNpoActivityActive(String userName, String npoType) {
    var response = client.isActiveNpoActivityActive(userName);
    Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    Assertions.assertEquals(npoType, Objects.requireNonNull(response.getBody()).getActivity());
  }

  @Then("у пользователя {string} нет действующих нпо")
  public void verifyNoActivityActive(String userName) {
    var response = client.isActiveNpoActivityActive(userName);
    Assertions.assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
  }

  private void verifyResponse(ResponseEntity<Void> npoStartResponse) {
    Assertions.assertTrue(
        npoStartResponse.getStatusCode().is2xxSuccessful(),
        "Waiting response code is 2xx, but was %s".formatted(npoStartResponse.getStatusCode()));
    getContext().putApiResponse(npoStartResponse);
  }
}
