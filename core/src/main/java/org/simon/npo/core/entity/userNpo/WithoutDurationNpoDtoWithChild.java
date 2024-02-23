package org.simon.npo.core.entity.userNpo;

import java.time.Instant;
import java.util.Set;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.WithChildNpoDictionary;

public class WithoutDurationNpoDtoWithChild extends WithoutDurationUserNpoDto implements UserNpo {

  WithoutDurationNpoDtoWithChild(UserNpoDto value) {
    super(value);
  }

  WithoutDurationNpoDtoWithChild(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull WithChildNpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant endTime,
      @NonNull Instant expectedEndTime,
      @NonNull String assigner,
      Set<String> childUserNames) {
    super(warehouseId, userName, dictionary, startTime, endTime, expectedEndTime, assigner);
    this.value.getChildUserNames().addAll(childUserNames);
  }
}
