package org.simon.npo.cucumber.step;

import io.cucumber.java.en.Given;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.simon.npo.cucumber.AbstractStepsDefinitions;
import org.simon.npo.core.service.AppDateTimeProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps extends AbstractStepsDefinitions {
  @Autowired
  AppDateTimeProvider appDateTimeProvider;

  @Given("сейчас {cucumberDate}")
  public void givenNow(LocalDateTime dateTime) {
    appDateTimeProvider.setClock(Clock.fixed(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC));
  }
}
