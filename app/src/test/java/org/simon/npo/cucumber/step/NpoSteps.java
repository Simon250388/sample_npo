package org.simon.npo.cucumber.step;

import io.cucumber.java.en.When;
import java.util.Set;
import org.simon.npo.NpoClient;
import org.simon.npo.cucumber.AbstractStepsDefinitions;
import org.simon.npo.dto.StartNpoRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class NpoSteps extends AbstractStepsDefinitions {
  @Autowired NpoClient client;

  @When("пользователь {string} начинает нпо {string}")
  public void startIndirectActivity(String userName, String npoType) {
        getContext()
        .putApiResponse(
            client.startActivity(
                StartNpoRequest.builder()
                    .withActivity(npoType)
                    .withUserNames(Set.of(userName))
                    .build()));
  }
}
