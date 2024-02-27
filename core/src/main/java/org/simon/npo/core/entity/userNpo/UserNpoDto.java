package org.simon.npo.core.entity.userNpo;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;

@Data
public class UserNpoDto {
  private final UserNpoKey id;
  private final Long warehouseId;
  private final String userName;
  protected final NpoDictionary npoDictionary;
  private final Instant startTime;
  private final Duration duration;
  private final LinkedList<UserNpoInterval> intervals = new LinkedList<>();
  private final String addWho;
  private final Set<String> childUserNames = new HashSet<>();

  public UserNpoDto(
      @NonNull UserNpoKey id,
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull NpoDictionary dictionary,
      @NonNull Instant startTime,
      @NonNull Instant expectedEndTime,
      @NonNull String assigner) {
    this(
        id,
        warehouseId,
        userName,
        dictionary,
        startTime,
        null,
        Duration.between(startTime, expectedEndTime),
        assigner);
  }

  public UserNpoDto(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull NpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant endTime,
      @NonNull Duration duration,
      @NonNull String assigner) {
    this(null, warehouseId, userName, dictionary, startTime, endTime, duration, assigner);
  }

  public UserNpoDto(
      @Nullable UserNpoKey id,
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull NpoDictionary dictionary,
      @NonNull Instant startTime,
      @Nullable Instant endTime,
      @NonNull Duration duration,
      @NonNull String assigner) {
    this(
        id,
        warehouseId,
        userName,
        dictionary,
        startTime,
        duration,
        Set.of(new UserNpoInterval(startTime, endTime, startTime.plus(duration), null)),
        assigner);
  }

  public UserNpoDto(
      @Nullable UserNpoKey id,
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull NpoDictionary dictionary,
      @NonNull Instant startTime,
      @NonNull Duration duration,
      @NonNull Collection<UserNpoInterval> intervals,
      @NonNull String assigner) {
    this.id = Optional.ofNullable(id).orElseGet(UserNpoKey::newVirtual);
    this.warehouseId = warehouseId;
    this.userName = userName;
    this.npoDictionary = dictionary;
    this.startTime = startTime;
    this.duration = duration;
    this.intervals.addAll(intervals);
    this.addWho = assigner;
  }

  public boolean isStarted(Instant now) {
    if (isCompletable(now)) {
      return false;
    }
    var lastInterval = this.intervals.getLast();
    var lastIntervalStartTime = lastInterval.startTime;
    return Objects.nonNull(lastIntervalStartTime) && !lastIntervalStartTime.isAfter(now);
  }

  public boolean isCompletable(Instant now) {
    var lastInterval = this.intervals.getLast();
    return Objects.nonNull(lastInterval.endTime);
  }

  public void complete(@NonNull Instant endTime, @NonNull String assigner) {
    var lastInterval = this.intervals.getLast();
    lastInterval.endTime = endTime;
  }

  protected void pause(UserNpo waitingComplete) {
    UserNpoDto newItem = waitingComplete.toDto();
    for (var item : this.intervals) {
      if (Objects.isNull(item.endTime)) {
        item.endTime = newItem.startTime;
        break;
      }
    }
    this.intervals.add(new UserNpoInterval(newItem.getId()));
  }

  protected void delayStart(UserNpo notCompletable) {
    if (this.intervals.size() != 1) {
      throw new IllegalStateException("User npo dto has more then one interval");
    }
    var item = this.intervals.iterator().next();
    item.delayStart(notCompletable.toDto().getId());
  }

  public boolean isPaused(Instant now) {
    if (isCompletable(now)) {
      return false;
    }
    var hasNotStartedWithDependency = hasNotStartedWithDependency();
    return hasNotStartedWithDependency && this.intervals.size() > 1;
  }

  public boolean startIsDelaying(Instant now) {
    var hasNotStartedWithDependency = hasNotStartedWithDependency();
    return hasNotStartedWithDependency && this.intervals.size() == 1;
  }

  private boolean hasNotStartedWithDependency() {
    return this.intervals.stream()
        .anyMatch(
            item ->
                Objects.isNull(item.getStartTime())
                    && Objects.isNull(item.getEndTime())
                    && Objects.isNull(item.getExpectedEndTime())
                    && Objects.nonNull(item.getDependentId()));
  }

  public void onDependencyComplete(UserNpoDto dependency) {
    var last = this.intervals.getLast();
    if (last.dependentId.equals(dependency.id)) {
      var durationOfCompletableIntervals = durationOfCompletableIntervals();
      last.startTime = dependency.getEndTime();
      last.endTime = null;
      last.expectedEndTime = last.startTime.plus(duration).minus(durationOfCompletableIntervals);
    }
  }

  public Instant getEndTime() {
    return this.intervals.getLast().endTime;
  }

  public Instant getPlannedEndTime() {
    return null;
  }

  private Duration durationOfCompletableIntervals() {
    return this.intervals.stream()
        .filter(item -> Objects.nonNull(item.startTime) && Objects.nonNull(item.endTime))
        .map(item -> Duration.between(item.startTime, item.endTime))
        .reduce(Duration.ofSeconds(0, 0), Duration::plus);
  }

  @EqualsAndHashCode
  public static class UserNpoKey {
    private static final AtomicInteger counter = new AtomicInteger();

    public UserNpoKey(Integer value) {
      this.value = value;
      this.virtual = false;
    }

    private UserNpoKey(Integer value, Boolean virtual) {
      this.value = value;
      this.virtual = virtual;
    }

    boolean virtual;
    Integer value;

    static UserNpoKey newVirtual() {
      return new UserNpoKey(counter.getAndIncrement(), true);
    }
  }

  @Data
  @AllArgsConstructor
  public static class UserNpoInterval {
    public UserNpoInterval(UserNpoKey dependentId) {
      this.dependentId = dependentId;
    }

    private Instant startTime;
    private Instant endTime;
    private Instant expectedEndTime;
    private UserNpoKey dependentId;

    public void delayStart(UserNpoKey dependentId) {
      if (Objects.nonNull(endTime)) {
        throw new IllegalStateException("Npo interval already complete");
      }
      this.startTime = null;
      this.expectedEndTime = null;
      this.dependentId = dependentId;
    }
  }
}
