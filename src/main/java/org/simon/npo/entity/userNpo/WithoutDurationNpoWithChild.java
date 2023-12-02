package org.simon.npo.entity.userNpo;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.Set;
import lombok.NonNull;
import org.simon.npo.entity.npoDictionary.WithChildNpoDictionary;

public class WithoutDurationNpoWithChild extends WithoutDurationUserNpo {
  private final Set<String> childUserNames;

  public WithoutDurationNpoWithChild(
      @NonNull String userName,
      @NonNull WithChildNpoDictionary dictionary,
      @NonNull Instant startTime,
      @NonNull Instant expectedEndTime,
      Set<String> childUserNames) {
    super(userName, dictionary, startTime, expectedEndTime);
    this.childUserNames = childUserNames;
  }

  public WithoutDurationNpoWithChild(
      @NonNull ResultSet rs,
      @NonNull WithChildNpoDictionary dictionary,
      Set<String> childUserNames) {
    super(rs, dictionary);
    this.childUserNames = childUserNames;
  }
}
