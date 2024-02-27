package org.simon.npo.config;

import java.time.Clock;
import org.simon.npo.core.service.AppDateTimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAppConfiguration {
  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  AppDateTimeProvider appDateTimeProvider(Clock clock) {
    return new AppDateTimeProvider(clock);
  }
}
