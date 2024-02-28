package org.simon.npo.core.entity.userNpo;

import java.time.Duration;
import java.time.Instant;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.npoDictionary.TaskmasterNpoDictionary;

public class WithoutDurationUserNpoDto implements UserNpo {

  protected final UserNpoDto value;

  WithoutDurationUserNpoDto(UserNpoDto value) {
    this.value = value;
  }

  WithoutDurationUserNpoDto(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull TaskmasterNpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant endTime,
      @NonNull Instant expectedEndTime,
      @NonNull String assigner) {
    var duration = Duration.between(startTime, expectedEndTime);
    this.value =
        new UserNpoDto(
            warehouseId, userName, dictionary, startTime, endTime, duration, assigner);
  }

  @Override
  public void complete(Instant endTime, @NotNull String assigner) {
    this.value.complete(endTime, assigner);
  }

  @Override
  public void complete(UserNpo userNpo, @NotNull String assigner) {
    if (!userNpo.getClass().isAssignableFrom(WithoutDurationUserNpoDto.class)) {
      throw new UnsupportedOperationException();
    }
    this.value.complete(userNpo.toDto().getStartTime(), assigner);
  }

  @Override
  public boolean isCompletable(Instant now) {
    return this.value.isCompletable(now);
  }

  @Override
  public boolean isStarted(Instant now) {
    return this.value.isStarted(now);
  }

  @Override
  public boolean isPaused(Instant now) {
    return this.value.isPaused(now);
  }

  @Override
  public boolean startIsDelaying(Instant now) {
    return this.value.startIsDelaying(now);
  }

  @Override
  public void pause(UserNpo waitingComplete) {
    this.value.pause(waitingComplete);
  }

  @Override
  public void delayStart(UserNpo notCompletable) {
    this.value.delayStart(notCompletable);
  }



  @Override
  public NpoDictionary getNpoType() {
    return this.value.getNpoDictionary();
  }

  @Override
  public UserNpoDto toDto() {
    return value;
  }

  @Override
  public void onDependencyComplete(UserNpo dependency) {
    this.value.onDependencyComplete(dependency.toDto());
  }
}
