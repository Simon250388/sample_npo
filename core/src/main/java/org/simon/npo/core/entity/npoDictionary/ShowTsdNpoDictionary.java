package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import org.jetbrains.annotations.Nullable;

public class ShowTsdNpoDictionary extends BaseNpoDictionary {
  public ShowTsdNpoDictionary(String name, Duration duration, boolean disable) {
    super(name, disable, false, true, duration);
  }

  @Override
  public @Nullable Duration duration() {
    return super.getDuration();
  }
}
