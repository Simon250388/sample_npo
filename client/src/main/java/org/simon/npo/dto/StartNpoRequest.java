package org.simon.npo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = StartNpoRequest.StartNpoRequestBuilder.class)
public class StartNpoRequest {
  Set<String> userNames;
  String activity;

  @JsonPOJOBuilder(withPrefix = "")
  public static class StartNpoRequestBuilder {}
}
