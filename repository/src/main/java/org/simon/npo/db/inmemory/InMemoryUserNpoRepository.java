package org.simon.npo.db.inmemory;

import com.google.common.annotations.VisibleForTesting;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.simon.npo.core.entity.userNpo.UserNpoDto;
import org.simon.npo.core.reposity.UserNpoRepository;

@RequiredArgsConstructor
public class InMemoryUserNpoRepository implements UserNpoRepository {
  private final Map<Long, Collection<UserNpoDto>> storage = new HashMap<>();

  public Collection<UserNpoDto> findByWarehouseAndUserNames(
      long warehouseId, Set<String> userNames) {
    var lowerCaseSet = userNames.stream().map(String::toLowerCase).collect(Collectors.toSet());
    return storage.getOrDefault(warehouseId, Collections.emptyList()).stream()
        .filter(item -> lowerCaseSet.contains(item.getUserName()))
        .toList();
  }

  @Override
  public void save(long warehouseId, List<UserNpoDto> records) {
    this.storage.merge(warehouseId, records, (o, n) -> n);
  }

  @VisibleForTesting
  public void clear() {
    this.storage.clear();
  }
}
