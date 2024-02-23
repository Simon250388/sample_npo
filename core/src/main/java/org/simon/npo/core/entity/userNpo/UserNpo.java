package org.simon.npo.core.entity.userNpo;

import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;

public interface UserNpo {
  void complete(Instant endTime, @NotNull String assigner);

  boolean isCompletable(Instant now);

  boolean isStarted(Instant now);

  void pause(UserNpo waitingComplete);
  boolean isPaused(Instant instant);

  void delayStart(UserNpo notCompletable);
  boolean startIsDelaying(Instant instant);

  NpoDictionary getNpoType();

  UserNpoDto toDto();

  void onDependencyComplete(UserNpo dependency);
}
