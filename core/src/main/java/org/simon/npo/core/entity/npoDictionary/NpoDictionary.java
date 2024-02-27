package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import org.jetbrains.annotations.Nullable;

public interface NpoDictionary {
  @Nullable Duration getDuration();
  String getName();
}
