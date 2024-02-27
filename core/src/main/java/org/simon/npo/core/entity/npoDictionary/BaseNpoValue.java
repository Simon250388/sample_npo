package org.simon.npo.core.entity.npoDictionary;

import java.time.Duration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@Builder(toBuilder = true)
public class BaseNpoValue {
  private String name;
  private boolean disable;
  private boolean hasChildren;
  private boolean showTsd;
  @Nullable Duration duration;

  public BaseNpoValue(String name, Duration duration) {
    this(name, false, false, true, duration);
  }

  public BaseNpoValue(String name) {
    this(name, false, false, false, null);
  }
}
