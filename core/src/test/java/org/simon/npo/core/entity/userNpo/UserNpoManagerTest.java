package org.simon.npo.core.entity.userNpo;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simon.npo.core.entity.npoDictionary.ShowTsdNpoDictionary;
import org.simon.npo.core.entity.npoDictionary.TaskmasterNpoDictionary;

class UserNpoManagerTest {

  private static final long WAREHOUSE_ID = 1;
  private static final String USER_NAME = "ivanov";
  private static final Clock clock =
      Clock.fixed(Instant.parse("2023-10-17T14:32:00Z"), ZoneOffset.UTC);
  private static final String ACTOR = "petrov";
  private static final ShowTsdNpoDictionary TYPE_LUNCH =
      new ShowTsdNpoDictionary("LUNCH", Duration.of(60, ChronoUnit.MINUTES), false);
  private static final TaskmasterNpoDictionary TYPE_OTHER =
      new TaskmasterNpoDictionary("PROCHIE_RABOTY", false);
  private static final UserNpoDto.UserNpoKey STARTED_WITHOUT_DURATION_ID =
      new UserNpoDto.UserNpoKey(147);

  @Test
  @DisplayName("Старт обеда когда нет других")
  public void successfulStartedLunchNotExistSavedItems() {
    var expectedItems = createManager(Collections.emptyList()).startActivity(TYPE_LUNCH).geItems();
    Assertions.assertThat(expectedItems)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(true);
  }

  @Test
  @DisplayName("Старт НПО прочее когда нет других")
  public void successfulStartedTypeOtherNotExistSavedItems() {
    var expectedItems =
        createManager(Collections.emptyList())
            .startActivity(TYPE_OTHER, Instant.parse("2023-10-18T00:32:00Z"))
            .geItems();
    Assertions.assertThat(expectedItems)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(true);
  }

  @Test
  @DisplayName("Начало обеда когда у сотрудника активное нпо с типом прочее")
  public void successfulStartedLunchWhenExistNotCompletableTypeOther() {
    var expectedItems =
        createManager(List.of(createStartedActivityForTypeOther()))
            .startActivity(TYPE_LUNCH)
            .geItems();
    Assertions.assertThat(expectedItems).hasSize(2);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof PresetDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(true);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof WithoutDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.isPaused(clock.instant()))
        .isEqualTo(true);
  }

  @Test
  @DisplayName("Начало c типом другое когда у сотрудника обед")
  public void successfulStartedTypeOtherWhenExistNotCompletableLunch() {
    var expectedItems =
        createManager(List.of(createNotCompletableLunch()))
            .startActivity(TYPE_OTHER, Instant.parse("2023-10-18T00:32:00Z"))
            .geItems();
    Assertions.assertThat(expectedItems).hasSize(2);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof PresetDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(true);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof WithoutDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.startIsDelaying(clock.instant()))
        .isEqualTo(true);
  }

  @Test
  @DisplayName("Начало c типом другое когда у сотрудника обед, после завершаем обед")
  public void startTypeOtherWhenExistNotCompletableLunchAfterCompleteLunch() {
    var expectedItems =
        createManager(List.of(createNotCompletableLunch()))
            .startActivity(TYPE_OTHER, Instant.parse("2023-10-18T00:32:00Z"))
            .completeActivity(TYPE_LUNCH, USER_NAME)
            .geItems();
    Assertions.assertThat(expectedItems).hasSize(2);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof PresetDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(false);
    Assertions.assertThat(expectedItems)
        .filteredOn(item -> item instanceof WithoutDurationUserNpoDto)
        .singleElement()
        .extracting(item -> item.isStarted(clock.instant()))
        .isEqualTo(true);
  }

  public UserNpoManager createManager(Collection<UserNpo> existingItems) {
    return new UserNpoManager(WAREHOUSE_ID, USER_NAME, ACTOR, clock, existingItems);
  }

  private UserNpo createNotCompletableLunch() {
    return new PresetDurationUserNpoDto(
        WAREHOUSE_ID, USER_NAME, TYPE_LUNCH, Instant.parse("2023-10-17T10:30:00Z"), ACTOR);
  }

  private UserNpo createStartedActivityForTypeOther() {
    return UserNpoFactory.create(
        new UserNpoDto(
            STARTED_WITHOUT_DURATION_ID,
            WAREHOUSE_ID,
            USER_NAME,
            TYPE_OTHER,
            Instant.parse("2023-10-17T10:30:00Z"),
            Instant.parse("2023-10-17T20:30:00Z"),
            ACTOR));
  }
}
