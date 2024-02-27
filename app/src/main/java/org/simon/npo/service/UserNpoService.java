package org.simon.npo.service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.simon.npo.core.entity.npoDictionary.NpoDictionary;
import org.simon.npo.core.entity.npoDictionary.NpoDictionaryFactory;
import org.simon.npo.core.entity.userNpo.UserNpo;
import org.simon.npo.core.entity.userNpo.UserNpoDto;
import org.simon.npo.core.entity.userNpo.UserNpoFactory;
import org.simon.npo.core.entity.userNpo.UserNpoManager;
import org.simon.npo.core.reposity.UserNpoRepository;
import org.simon.npo.core.service.AppDateTimeProvider;
import org.simon.npo.dto.UserNpoResponse;
import org.simon.npo.dto.UserNpoStartRequest;
import org.simon.npo.mapper.UserNpoMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNpoService {
  private static final String ACTOR = "SYSTEM";
  private final AppDateTimeProvider appDateTimeProvider;
  private final NpoDictionaryService npoDictionaryService;
  private final UserNpoRepository userNpoRepository;

  public void start(String warehouse, UserNpoStartRequest request) {
    var warehouseId = Long.parseLong(warehouse);
    var dictionary =
        NpoDictionaryFactory.create(npoDictionaryService.getByName(request.getActivity()));
    var userNames = request.getUserNames();
    var activitiesByUser =
        userNpoRepository.findByWarehouseAndUserNames(warehouseId, userNames).stream()
            .collect(
                Collectors.groupingBy(
                    UserNpoDto::getUserName,
                    Collectors.mapping(UserNpoFactory::create, Collectors.toList())));
    var records =
        userNames.stream()
            .map(
                userName -> {
                  var plannedEndTime =
                      Optional.ofNullable(request.getPlannedEndTime())
                          .map(OffsetDateTime::toInstant)
                          .orElse(null);
                  var activities = activitiesByUser.getOrDefault(userName, Collections.emptyList());
                  return startActivityByUser(
                      warehouseId, userName, dictionary, plannedEndTime, activities);
                })
            .flatMap(manager -> manager.geItems().stream())
            .map(UserNpo::toDto)
            .toList();
    userNpoRepository.save(warehouseId, records);
  }

  @Nullable
  public UserNpoResponse getActive(String warehouse, String userName) {
    var warehouseId = Long.parseLong(warehouse);
    var activities =
        userNpoRepository.findByWarehouseAndUserNames(warehouseId, Set.of(userName)).stream()
            .map(UserNpoFactory::create)
            .toList();
    var manager = new UserNpoManager(warehouseId, userName, ACTOR, appDateTimeProvider, activities);
    return Optional.ofNullable(manager.getActive()).map(UserNpoMapper::mapToResponse).orElse(null);
  }

    private UserNpoManager startActivityByUser(
            long warehouseId,
            String userName,
            NpoDictionary dictionary,
            Instant plannedEndTime,
            List<UserNpo> activities) {
        var manager = new UserNpoManager(warehouseId, userName, ACTOR, appDateTimeProvider, activities);
        return manager.startActivity(dictionary, plannedEndTime);
    }
}
