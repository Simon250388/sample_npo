package org.simon.npo.config

import org.simon.npo.core.reposity.NpoDictionaryRepository
import org.simon.npo.core.reposity.UserNpoRepository
import org.simon.npo.db.inmemory.InMemoryNpoDictionaryRepository
import org.simon.npo.db.inmemory.InMemoryUserNpoRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {
    @Bean
    fun userNpoRepository(): UserNpoRepository = InMemoryUserNpoRepository()

    @Bean
    fun npoDictionaryRepository(): NpoDictionaryRepository = InMemoryNpoDictionaryRepository()
}
