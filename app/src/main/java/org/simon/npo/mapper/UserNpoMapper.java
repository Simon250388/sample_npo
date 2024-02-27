package org.simon.npo.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.simon.npo.core.entity.userNpo.UserNpo;
import org.simon.npo.dto.UserNpoResponse;

public class UserNpoMapper {
    public static UserNpoResponse mapToResponse(UserNpo userNpo) {
        var userNpoValue = userNpo.toDto();
        return UserNpoResponse.builder()
                .withUserName(userNpoValue.getUserName())
                .withActivity(userNpoValue.getNpoDictionary().getName())
                .withStartTime(OffsetDateTime.ofInstant(userNpoValue.getStartTime(), ZoneOffset.UTC))
                .withEndTime(
                        Optional.ofNullable(userNpoValue.getEndTime())
                                .map(value -> OffsetDateTime.ofInstant(value, ZoneOffset.UTC))
                                .orElse(null))
                .withPlannedEndTime(
                        Optional.ofNullable(userNpoValue.getPlannedEndTime())
                                .map(value -> OffsetDateTime.ofInstant(value, ZoneOffset.UTC))
                                .orElse(null))
                .build();
    }
}
