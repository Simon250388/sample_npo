package org.simon.npo.entity.userNpo;

import java.sql.ResultSet;
import java.time.Instant;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.simon.npo.entity.npoDictionary.TaskmasterNpoDictionary;

public class WithoutDurationUserNpo extends BaseUserNpo implements Cloneable {

  public WithoutDurationUserNpo(
      @NonNull String userName,
      @NonNull TaskmasterNpoDictionary dictionary,
      @NonNull Instant startTime,
      @NonNull Instant expectedEndTime) {
    super(userName, dictionary, startTime, expectedEndTime);
  }

  public WithoutDurationUserNpo(@NonNull ResultSet rs, @NonNull TaskmasterNpoDictionary dictionary) {
    super(rs, dictionary);
  }

  public WithoutDurationUserNpo(@NonNull WithoutDurationUserNpo toClone) {
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
  public void start(@NotNull Instant startTime) {
    super.start(startTime);
  }

  @Override
  @SneakyThrows
  public UserNpo clone() {
    return new WithoutDurationUserNpo(this);
  }
}
