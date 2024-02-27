package org.simon.npo.cucumber.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CommonTestAppConfiguration {
    @Bean
    @Primary
    Clock testClock() {
        return Clock.fixed(Instant.parse("2024-02-01T15:32:23Z"), ZoneOffset.UTC);
    }
}
