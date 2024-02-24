package org.simon.npo.cucumber.config;

import org.simon.npo.NpoClient;
import org.simon.npo.NpoClientImpl;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
  private static final String WAREHOUSE_ID = "testWarehouse";


  @Bean
  NpoClient npoClient(TestRestTemplate testRestTemplate) {
    return new NpoClientImpl(WAREHOUSE_ID, testRestTemplate.getRestTemplate());
  }
}
