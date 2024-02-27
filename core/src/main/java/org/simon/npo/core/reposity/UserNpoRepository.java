package org.simon.npo.core.reposity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.simon.npo.core.entity.userNpo.UserNpoDto;

public interface UserNpoRepository {
    Collection<UserNpoDto> findByWarehouseAndUserNames(long warehouseId, Set<String> userNames);

    void save(long warehouseId, List<UserNpoDto> records);
}
