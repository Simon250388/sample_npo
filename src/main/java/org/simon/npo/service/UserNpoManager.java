package org.simon.npo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.entity.npoDictionary.NpoDictionary;
import org.simon.npo.entity.userNpo.UserNpo;
import org.simon.npo.helper.UserNpoFactory;
import org.simon.npo.helper.intervalIntersectionsBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNpoManager {
  private final CrudRepository<UserNpo, Long> userNpoRepository;

  public Collection<UserNpo> getAllNotCompletable(String userName) {
    return Collections.emptyList();
  }

  public Collection<UserNpo> getAllNotCompletable(String userName, NpoDictionary dictionary) {
    return Collections.emptyList();
  }

  public void start(
      String userName,
      NpoDictionary npoDictionary,
      Instant startTime,
      Instant expectedEndTime,
      Set<String> childUserNames) {
    var newUserNpo =
        UserNpoFactory.create(userName, npoDictionary, startTime, expectedEndTime, childUserNames);
    var notCompletable = getAllNotCompletable(userName);
    var intersections =
        new intervalIntersectionsBuilder()
            .intersections(Stream.concat(notCompletable.stream(), Stream.of(newUserNpo)).toList());
    var modified = new HashSet<>(List.of(newUserNpo));
    for (var pair : intersections) {
      var newItem = merge(pair.getLeft(), pair.getRight());
      Optional.ofNullable(newItem).ifPresent(modified::add);
    }
    userNpoRepository.saveAll(modified);
  }

  public void complete(String userName, NpoDictionary npoDictionary, Instant endTime) {
    var notCompletable = getAllNotCompletable(userName, npoDictionary);
    for (var userNpo : notCompletable) {
      userNpo.complete(endTime);
    }
  }

  @SneakyThrows
  @Nullable
  public static UserNpo merge(UserNpo left, UserNpo right) {
    UserNpo newItem = null;
    if (left.isComplete()) {
      right.start(left.getEndTime());
    } else {
      left.complete(right.getStartTime());
      if (left.getExpectedEndTime().isAfter(right.getEndOrExpectedEndTime())) {
        right.complete(right.getExpectedEndTime());
        newItem = left.clone();
        newItem.start(right.getEndTime());
        newItem.increaseExpectedEndTime(right.durationMinute(), ChronoUnit.MINUTES);
      }
    }
    return newItem;
  }
}
