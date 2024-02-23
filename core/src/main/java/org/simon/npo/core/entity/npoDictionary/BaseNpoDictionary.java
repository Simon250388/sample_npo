package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
public class BaseNpoDictionary implements NpoDictionary {
  String name;
  boolean disable;
  boolean hasChildren;
  boolean showTsd;
  @Nullable Duration duration;

  @Override
  public @Nullable Duration duration() {
    throw new UnsupportedOperationException();
  }
}
