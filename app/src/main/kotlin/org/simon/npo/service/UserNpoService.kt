package org.simon.npo.service

import org.simon.npo.core.entity.npoDictionary.NpoDictionaryFactory
import org.simon.npo.core.entity.userNpo.UserNpo
import org.simon.npo.core.entity.userNpo.UserNpoFactory
import org.simon.npo.core.entity.userNpo.UserNpoManager
import org.simon.npo.core.reposity.UserNpoRepository
import org.simon.npo.core.service.AppDateTimeProvider
import org.simon.npo.dto.UserNpoResponse
import org.simon.npo.dto.UserNpoStartRequest
import org.simon.npo.mapper.toResponse
import org.springframework.stereotype.Service


const val ACTOR = "SYSTEM"

@Service
class UserNpoService(
    private val appDateTimeProvider: AppDateTimeProvider,
    private val npoDictionaryService: NpoDictionaryService,
    private val userNpoRepository: UserNpoRepository
) {
    fun start(warehouse: String, request: UserNpoStartRequest?) {
        val warehouseId = warehouse.toLong()
        val dictionary = NpoDictionaryFactory.create(npoDictionaryService.getByName(request!!.activity))
        val userNames = request.userNames
        val plannedEndTime = request.plannedEndTime?.toInstant()
        val startTime = request.startTime?.toInstant()
        val existingRecords = userNpoRepository.findByWarehouseAndUserNames(warehouseId, userNames)
            .groupBy({ it.userName }, { UserNpoFactory.create(it) })
        userNames
            .map { createManager(warehouseId, it, existingRecords[it] ?: emptyList()) }
            .map { it.startActivity(dictionary, startTime, plannedEndTime) }
            .flatMap { it.geItems() }
            .map { it.toDto() }
            .let { userNpoRepository.save(warehouseId, it) }
    }

    fun getActive(warehouse: String, userName: String): UserNpoResponse? =
        warehouse.toLong()
            .let { mapOf(it to userNpoRepository.findByWarehouseAndUserNames(it, setOf(userName))) }
            .mapValues { entry -> entry.value.map { UserNpoFactory.create(it) } }
            .map { createManager(it.key, userName, it.value) }
            .map { it.active }
            .firstNotNullOfOrNull { it?.toResponse() }

    private fun createManager(warehouseId: Long, userName: String, activities: List<UserNpo>): UserNpoManager =
        UserNpoManager(warehouseId, userName, ACTOR, appDateTimeProvider, activities)
}
