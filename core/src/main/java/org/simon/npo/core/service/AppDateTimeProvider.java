package org.simon.npo.core.service;

import com.google.common.annotations.VisibleForTesting;
import java.time.Clock;
import lombok.Getter;

@Getter
public class AppDateTimeProvider {
  private Clock clock;

  public AppDateTimeProvider(Clock clock) {
    this.clock = clock;
  }

  @VisibleForTesting
  public void setClock(Clock clock) {
    this.clock = clock;
  }

}
