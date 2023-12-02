package org.simon.npo.entity.userNpo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.NonNull;

public interface UserNpo {
  boolean isComplete();

  Instant getStartTime();

  void start(@NonNull Instant startTime);

  void complete(Instant endTime);

  Instant getEndTime();

  Instant getExpectedEndTime();

  void setExpectedEndTime(Instant expectedEndTime);

  Instant getEndOrExpectedEndTime();

  long durationMinute();

  void increaseExpectedEndTime(long amountToAdd, @NonNull ChronoUnit unit);

  UserNpo clone();
}
