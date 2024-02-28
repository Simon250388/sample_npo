package org.simon.npo.core.entity.userNpo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.service.AppDateTimeProvider;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.userNpo.exception.ExistOtherUserNpoException;
import org.simon.npo.core.entity.userNpo.exception.UserNpoNotFoundException;
import org.simon.npo.core.entity.userNpo.validator.MaxCountStartValidator;
import org.simon.npo.core.entity.userNpo.validator.MaxNpoDurationValidator;

public class UserNpoManager {
  private final Long warehouseId;
  private final String userName;
  private final String actor;
  private final AppDateTimeProvider appDateTimeProvider;
  private Optional<UserNpo> mayBeNotCompletable = Optional.empty();
  private final Set<UserNpo> notStarted = new HashSet<>();
  private final List<UserNpo> items = new ArrayList<>();
  private final Map<NpoDictionary, Integer> countStarts = new HashMap<>();
  private final MaxCountStartValidator maxCountStartValidator = new MaxCountStartValidator();
  private final MaxNpoDurationValidator maxNpoDurationValidator = new MaxNpoDurationValidator();

  public UserNpoManager(
      @NonNull Long warehouseId,
      @NonNull String userName,
      @NonNull String actor,
      @NonNull AppDateTimeProvider appDateTimeProvider,
      @NonNull Collection<UserNpo> items) {
    this.warehouseId = warehouseId;
    this.userName = userName;
    this.actor = actor;
    this.appDateTimeProvider = appDateTimeProvider;
    initItems(items);
  }

  public UserNpoManager startActivity(@NonNull NpoDictionary npoDictionary) {
    return startActivity(npoDictionary, null);
  }

  public UserNpoManager startActivity(
      @NonNull NpoDictionary npoDictionary, Instant plannedEndTime) {
    return startActivity(npoDictionary, null, plannedEndTime);
  }

  public UserNpoManager startActivity(
      NpoDictionary npoDictionary,
      @Nullable Instant mayBeStartTime,
      @Nullable Instant plannedEndTime) {
    var startTime =
        Optional.ofNullable(mayBeStartTime).orElse(appDateTimeProvider.getClock().instant());
    initNotCompletable();
    addValidateError(npoDictionary, startTime, plannedEndTime);
    var newItem =
        UserNpoFactory.create(
            warehouseId,
            userName,
            npoDictionary,
            startTime,
            plannedEndTime,
            Collections.emptySet(),
            actor);

    var isStarted = newItem.isStarted(appDateTimeProvider.getClock().instant());
    if (isStarted) {
      fixExistIntersectionProblems(newItem, startTime);
    }
    addNewItemIfNotExist(newItem, isStarted);
    return this;
  }

  public UserNpoManager completeActivity(NpoDictionary npoDictionary, String actor) {
    var now = Instant.now(appDateTimeProvider.getClock());
    return completeActivity(npoDictionary, now, actor);
  }

  public UserNpoManager completeActivity(
      NpoDictionary npoDictionary, Instant endTime, String actor) {
    initNotCompletable();
    var notCompletable =
        this.mayBeNotCompletable
            .filter(userNpo -> userNpo.getNpoType().equals(npoDictionary))
            .orElseThrow(UserNpoNotFoundException::new);
    notCompletable.complete(endTime, actor);
    this.mayBeNotCompletable = Optional.empty();
    this.notStarted.forEach(item -> item.onDependencyComplete(notCompletable));
    return this;
  }

  public Collection<UserNpo> geItems() {
    return this.items;
  }

  private void initItems(Collection<UserNpo> items) {
    for (var item : items) {
      this.items.add(item);
      this.countStarts.merge(item.getNpoType(), 1, Integer::sum);
    }
  }

  private void initNotCompletable() {
    var now = appDateTimeProvider.getClock().instant();
    for (var item : items) {
      if (!item.isCompletable(now) && !item.isStarted(now)) {
        this.notStarted.add(item);
      } else if (!item.isCompletable(now)) {
        this.mayBeNotCompletable = Optional.of(item);
      }
    }
  }

  private void fixExistIntersectionProblems(UserNpo newItem, Instant startTime) {
    if (this.mayBeNotCompletable.isPresent()) {
      var notCompletable = this.mayBeNotCompletable.get();
      try {
        notCompletable.complete(startTime, actor);
      } catch (IllegalArgumentException illegalArgumentException) {
        try {
          notCompletable.pause(newItem);
        } catch (UnsupportedOperationException notCompletableException) {
          newItem.delayStart(notCompletable);
        }
      }
    }
  }

  private void addValidateError(
      NpoDictionary npoDictionary, Instant startTime, @Nullable Instant plannedEndTime) {
    var allReadyStartedCount = countStarts.getOrDefault(npoDictionary, 0);
    var allReadyStartedError = maxCountStartValidator.isValid(npoDictionary, allReadyStartedCount);
    if (Objects.nonNull(allReadyStartedError)) {
      throw allReadyStartedError;
    }

    var maxNpoDurationError =
        maxNpoDurationValidator.isValid(npoDictionary, startTime, plannedEndTime);
    if (Objects.nonNull(maxNpoDurationError)) {
      throw maxNpoDurationError;
    }
  }

  private void addNewItemIfNotExist(UserNpo newItem, boolean isStarted) {
    if (this.items.contains(newItem)) {
      throw new ExistOtherUserNpoException();
    }
    this.items.add(newItem);
    if (isStarted) {
      this.mayBeNotCompletable = Optional.of(newItem);
    }
  }

  @Nullable
  public UserNpo getActive() {
    initNotCompletable();
    return this.mayBeNotCompletable.orElse(null);
  }
}
