package org.simon.npo.entity.npoDictionary;

import lombok.Getter;
import lombok.Value;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.entity.npoDictionary.NpoDictionary;

@Getter
public class BaseNpoDictionary implements NpoDictionary {
  String name;
  String description;
  boolean disable;
  boolean hasChildren;
  boolean showTsd;
  @Nullable Long duration;

  @Override
  public @Nullable Long duration() {
    throw new UnsupportedOperationException();
  }
}
