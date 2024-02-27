package org.simon.npo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserNpoStartRequest.UserNpoStartRequestBuilder.class)
public class UserNpoStartRequest {
  Set<String> userNames;
  String activity;
  @Builder.Default OffsetDateTime startTime = null;
  @Builder.Default OffsetDateTime plannedEndTime = null;

  @JsonPOJOBuilder
  public static class UserNpoStartRequestBuilder {}
}
