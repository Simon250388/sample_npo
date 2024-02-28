package org.simon.npo.mapper

import org.simon.npo.core.entity.userNpo.UserNpo
import org.simon.npo.dto.UserNpoResponse
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun UserNpo.toResponse(): UserNpoResponse {
    val userNpoValue = toDto()
    return UserNpoResponse.builder()
        .withUserName(userNpoValue.userName)
        .withActivity(userNpoValue.npoDictionary.name)
        .withStartTime(OffsetDateTime.ofInstant(userNpoValue.startTime, ZoneOffset.UTC))
        .withEndTime(userNpoValue.endTime?.let { OffsetDateTime.ofInstant(it, ZoneOffset.UTC) })
        .withPlannedEndTime(userNpoValue.plannedEndTime?.let { OffsetDateTime.ofInstant(it, ZoneOffset.UTC) })
        .build()
}
