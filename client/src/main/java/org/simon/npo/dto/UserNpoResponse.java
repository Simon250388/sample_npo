package org.simon.npo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
@JsonDeserialize(builder = UserNpoResponse.UserNpoResponseBuilder.class)
public class UserNpoResponse {
  String userName;
  String activity;
  OffsetDateTime startTime;
  OffsetDateTime endTime;
  OffsetDateTime plannedEndTime;
  @JsonPOJOBuilder
  public static class UserNpoResponseBuilder {}
}
