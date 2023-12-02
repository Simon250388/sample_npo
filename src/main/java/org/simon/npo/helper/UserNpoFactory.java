package org.simon.npo.helper;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import lombok.SneakyThrows;
import org.simon.npo.entity.npoDictionary.NpoDictionary;
import org.simon.npo.entity.npoDictionary.ShowTsdNpoDictionary;
import org.simon.npo.entity.npoDictionary.TaskmasterNpoDictionary;
import org.simon.npo.entity.npoDictionary.WithChildNpoDictionary;
import org.simon.npo.entity.userNpo.PresetDurationUserNpo;
import org.simon.npo.entity.userNpo.UserNpo;
import org.simon.npo.entity.userNpo.WithoutDurationNpoWithChild;
import org.simon.npo.entity.userNpo.WithoutDurationUserNpo;
import org.simon.npo.service.NpoDictionaryManager;

public class UserNpoFactory {
  public static UserNpo create(
      String userName,
      NpoDictionary npoDictionary,
      Instant startTime,
      Instant expectedEndTime,
      Set<String> childUserNames) {

    if (npoDictionary instanceof ShowTsdNpoDictionary showTsdNpoDictionary) {
      return new PresetDurationUserNpo(userName, showTsdNpoDictionary, startTime, expectedEndTime);
    } else if (npoDictionary instanceof WithChildNpoDictionary withChildNpoDictionary) {
      return new WithoutDurationNpoWithChild(
          userName, withChildNpoDictionary, startTime, expectedEndTime, childUserNames);
    } else if (npoDictionary instanceof TaskmasterNpoDictionary taskmasterNpoDictionary) {
      return new WithoutDurationUserNpo(
          userName, taskmasterNpoDictionary, startTime, expectedEndTime);
    }
    throw new UnsupportedOperationException("");
  }

  @SneakyThrows
  public static UserNpo create(ResultSet rs, NpoDictionaryManager npoDictionaryManager) {
    var npoDictionary = npoDictionaryManager.getByName(rs.getString(""));
    if (npoDictionary instanceof ShowTsdNpoDictionary showTsdNpoDictionary) {
      return new PresetDurationUserNpo(rs, showTsdNpoDictionary);
    } else if (npoDictionary instanceof WithChildNpoDictionary withChildNpoDictionary) {
      return new WithoutDurationNpoWithChild(rs, withChildNpoDictionary, Collections.emptySet());
    } else if (npoDictionary instanceof TaskmasterNpoDictionary taskmasterNpoDictionary) {
      return new WithoutDurationUserNpo(rs, taskmasterNpoDictionary);
    }
    throw new UnsupportedOperationException("");
  }
}
