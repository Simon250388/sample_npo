package org.simon.npo.core.entity.userNpo.validator;

import java.time.Instant;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.userNpo.exception.BaseNpoException;

public class MaxNpoDurationValidator {

  public <T extends BaseNpoException> T isValid(
      NpoDictionary dictionary, Instant startTime, @Nullable Instant plannedEndTime) {
    return null;
  }
}
