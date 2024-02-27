package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

public class TaskmasterNpoDictionary implements NpoDictionary {
  protected final BaseNpoValue value;

  @VisibleForTesting
  public TaskmasterNpoDictionary(String name, boolean disable) {
    this.value = new BaseNpoValue(name, disable, false, false, null);
  }

  TaskmasterNpoDictionary(BaseNpoValue value) {
    this.value = value.toBuilder().build();
  }

  @Override
  public @Nullable Duration getDuration() {
    return null;
  }

  @Override
  public String getName() {
    return value.getName();
  }
}
