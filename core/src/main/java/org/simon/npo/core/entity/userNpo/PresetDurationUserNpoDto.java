package org.simon.npo.core.entity.userNpo;

import java.time.Instant;
import java.util.Objects;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.npoDictionary.ShowTsdNpoDictionary;

public class PresetDurationUserNpoDto implements UserNpo {
  private final UserNpoDto value;

  PresetDurationUserNpoDto(UserNpoDto value) {
    this.value = value;
  }

  PresetDurationUserNpoDto(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull ShowTsdNpoDictionary dictionary,
      @NonNull Instant startTime,
      @NonNull String assigner) {
    Objects.requireNonNull(dictionary.getDuration());
    this.value =
        new UserNpoDto(
            warehouseId, userName, dictionary, startTime, null, dictionary.getDuration(), assigner);
  }

  PresetDurationUserNpoDto(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull ShowTsdNpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant endTime,
      @NonNull String assigner) {
    Objects.requireNonNull(dictionary.getDuration());
    this.value =
        new UserNpoDto(
            warehouseId,
            userName,
            dictionary,
            startTime,
            endTime,
            dictionary.getDuration(),
            assigner);
  }

  @Override
  public void complete(@NonNull Instant endTime, @NonNull String assigner) {
    if (!this.value.getUserName().equalsIgnoreCase(assigner)) {
      throw new IllegalArgumentException(
          "User npo can complete only at expected end time or only the one who was started");
    }
    this.value.complete(endTime, assigner);
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
  public void pause(UserNpo waitingComplete) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isPaused(Instant instant) {
    return false;
  }

  @Override
  public void delayStart(UserNpo notCompletable) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean startIsDelaying(Instant instant) {
    return false;
  }

  @Override
  public NpoDictionary getNpoType() {
    return this.value.npoDictionary;
  }

  @Override
  public UserNpoDto toDto() {
    return this.value;
  }

  @Override
  public void onDependencyComplete(UserNpo dependency) {
    throw new UnsupportedOperationException();
  }
}
