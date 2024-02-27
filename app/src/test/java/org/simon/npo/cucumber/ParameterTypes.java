package org.simon.npo.cucumber;

import io.cucumber.java.ParameterType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParameterTypes {
  @ParameterType("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")
  public LocalDateTime cucumberDate(String dateString) {
    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
