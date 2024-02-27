package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

public class ShowTsdNpoDictionary implements NpoDictionary  {
  private final BaseNpoValue value;
  @VisibleForTesting
  public ShowTsdNpoDictionary(String name, Duration duration, boolean disable) {
    this.value = new BaseNpoValue(name, disable, false, true, duration);
  }

  ShowTsdNpoDictionary(BaseNpoValue value) {
    this.value = value.toBuilder().build();
  }

  @Override
  public @Nullable Duration getDuration() {
    return value.getDuration();
  }

  @Override
  public String getName() {
    return value.getName();
  }
}
