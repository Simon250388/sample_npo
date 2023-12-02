package org.simon.npo.entity.userNpo;

import java.sql.ResultSet;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.entity.npoDictionary.NpoDictionary;
import org.simon.npo.entity.npoDictionary.ShowTsdNpoDictionary;
import org.simon.npo.helper.Interval;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@AllArgsConstructor
public abstract class BaseUserNpo implements Interval, UserNpo {
  private Long id;
  private final String userName;
  protected final NpoDictionary npoDictionary;
  private Instant startTime;
  private Instant endTime;
  private Instant expectedEndTime;
  private String addWho;
  private String editWho;
  private Instant addDate;
  private Instant editDate;

  public BaseUserNpo(
      @NonNull String userName,
      @NonNull NpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant expectedEndTime) {
    this.userName = userName;
    this.npoDictionary = dictionary;
    this.startTime = startTime;
    this.expectedEndTime = expectedEndTime;
  }

  @SneakyThrows
  public BaseUserNpo(@NonNull ResultSet rs, @NonNull NpoDictionary dictionary) {
    this.userName = rs.getString("user_name");
    this.npoDictionary = dictionary;
  }

  @Override
  public boolean isComplete() {
    return Objects.nonNull(endTime);
  }

  @Override
  public void start(@NotNull Instant startTime) {
    this.startTime = startTime;
    this.editWho = SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public Instant getEndOrExpectedEndTime() {
    return Optional.ofNullable(endTime).orElse(expectedEndTime);
  }

  @Override
  public void setExpectedEndTime(Instant expectedEndTime) {
    this.expectedEndTime = expectedEndTime;
    this.editWho = SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public void complete(Instant endTime) {
    this.endTime = endTime;
    this.editWho = SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public UserNpo clone() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void increaseExpectedEndTime(long amountToAdd, @NonNull ChronoUnit unit) {
    this.expectedEndTime = this.expectedEndTime.plus(amountToAdd, unit);
    this.editWho = SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public long durationMinute() {
    return Duration.between(startTime, intervalEnd()).toMinutes();
  }

  @Override
  public Instant intervalStart() {
    return startTime;
  }

  @Override
  public Instant intervalEnd() {
    return Optional.ofNullable(endTime).orElse(expectedEndTime);
  }
}
