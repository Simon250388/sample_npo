package org.simon.npo.config

import org.simon.npo.core.service.AppDateTimeProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class CommonAppConfiguration {
    @Bean
    fun clock(): Clock = Clock.systemUTC()

    @Bean
    fun appDateTimeProvider(clock: Clock): AppDateTimeProvider = AppDateTimeProvider(clock)
}
