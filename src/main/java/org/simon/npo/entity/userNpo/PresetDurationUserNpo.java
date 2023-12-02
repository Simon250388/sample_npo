package org.simon.npo.entity.userNpo;

import java.sql.ResultSet;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.entity.npoDictionary.ShowTsdNpoDictionary;
import org.springframework.security.core.context.SecurityContextHolder;

public class PresetDurationUserNpo extends BaseUserNpo {

  public PresetDurationUserNpo(
      @NonNull String userName,
      @NonNull ShowTsdNpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant expectedEndTime) {
    super(userName, dictionary, startTime, expectedEndTime);
    setExpectedEndTime(calcExpectedEndTime(startTime));
  }

  public PresetDurationUserNpo(@NonNull ResultSet rs, @NonNull ShowTsdNpoDictionary dictionary) {
    super(rs, dictionary);
  }

  public PresetDurationUserNpo(@NonNull PresetDurationUserNpo toClone) {
    super(
        toClone.getId(),
        toClone.getUserName(),
        toClone.getNpoDictionary(),
        toClone.getStartTime(),
        toClone.getEndTime(),
        toClone.getExpectedEndTime(),
        toClone.getAddWho(),
        toClone.getEditWho(),
        toClone.getAddDate(),
        toClone.getEditDate());
  }

  @Override
  public void start(@NonNull Instant startTime) {
    var expectedEndTime = calcExpectedEndTime(startTime);
    super.start(startTime);
    super.setExpectedEndTime(expectedEndTime);
  }

  @Override
  public void increaseExpectedEndTime(long count, @NonNull ChronoUnit unit) {
    throw new UnsupportedOperationException(
        "Npo with preset duration not support change operation expected end time operation");
  }

  @Override
  public void complete(Instant endTime) {
    var expectedEndTime = calcExpectedEndTime(getStartTime());
    if (!endTime.equals(expectedEndTime)
        || getUserName()
            .equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())) {
      throw new IllegalArgumentException(
          "User npo can complete only at expected end time or only the one who was started");
    }
    super.complete(endTime);
  }

  @Override
  public UserNpo clone() {
    return new PresetDurationUserNpo(this);
  }

  private Instant calcExpectedEndTime(Instant startTime) {
    //noinspection DataFlowIssue
    return startTime.plus(npoDictionary.duration(), ChronoUnit.MINUTES);
  }
}
