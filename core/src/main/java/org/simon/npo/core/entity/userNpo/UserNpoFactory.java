package org.simon.npo.core.entity.userNpo;

import java.time.Instant;
import java.util.Set;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.npoDictionary.ShowTsdNpoDictionary;
import org.simon.npo.core.entity.npoDictionary.TaskmasterNpoDictionary;
import org.simon.npo.core.entity.npoDictionary.WithChildNpoDictionary;

public class UserNpoFactory {

  static UserNpo create(
      Long warehouseId,
      String userName,
      NpoDictionary npoDictionary,
      Instant startTime,
      Instant expectedEndTime,
      Set<String> childUserNames,
      String assigner) {

    return create(
        warehouseId,
        userName,
        npoDictionary,
        startTime,
            expectedEndTime,
        null,
        childUserNames,
        assigner);
  }

  static UserNpo create(
      Long warehouseId,
      String userName,
      NpoDictionary npoDictionary,
      Instant startTime,
      Instant expectedEndTime,
      Instant endTime,
      Set<String> childUserNames,
      String assigner) {

    if (npoDictionary instanceof ShowTsdNpoDictionary showTsdNpoDictionary) {
      return new PresetDurationUserNpoDto(
          warehouseId,
          userName,
          showTsdNpoDictionary,
          startTime,
          endTime,
          assigner);
    } else if (npoDictionary instanceof WithChildNpoDictionary withChildNpoDictionary) {
      return new WithoutDurationNpoDtoWithChild(
          warehouseId,
          userName,
          withChildNpoDictionary,
          startTime,
          expectedEndTime,
          endTime,
          assigner,
          childUserNames);
    } else if (npoDictionary instanceof TaskmasterNpoDictionary taskmasterNpoDictionary) {
      return new WithoutDurationUserNpoDto(
          warehouseId,
          userName,
          taskmasterNpoDictionary,
          startTime,
          endTime,
          expectedEndTime,
          assigner);
    }
    throw new UnsupportedOperationException("");
  }

  public static UserNpo create(UserNpoDto userNpoDto) {
    if (userNpoDto.getNpoDictionary() instanceof ShowTsdNpoDictionary) {
     return new PresetDurationUserNpoDto(userNpoDto);
    } else if (userNpoDto.getNpoDictionary() instanceof WithChildNpoDictionary) {
      return new WithoutDurationNpoDtoWithChild(userNpoDto);
    } else if (userNpoDto.getNpoDictionary() instanceof TaskmasterNpoDictionary) {
      return new WithoutDurationUserNpoDto(userNpoDto);
    }
    throw new UnsupportedOperationException("");
  }
}
