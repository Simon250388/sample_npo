package org.simon.npo.cucumber.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@Configuration
class CommonTestAppConfiguration {
    @Bean
    @Primary
    fun testClock(): Clock = Clock.fixed(Instant.parse("2024-02-01T15:32:23Z"), ZoneOffset.UTC)
}
