package org.simon.npo.service

import org.simon.npo.core.entity.npoDictionary.BaseNpoValue
import org.simon.npo.core.reposity.NpoDictionaryRepository
import org.springframework.stereotype.Service

@Service
class NpoDictionaryService(private val npoDictionaryRepository: NpoDictionaryRepository) {
    fun getByName(activity: String): BaseNpoValue = npoDictionaryRepository.findByName(activity)
}
