package org.simon.npo.cucumber.config

import org.simon.npo.NpoClient
import org.simon.npo.NpoClientImpl
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfiguration {
    @Bean
    fun npoClient(testRestTemplate: TestRestTemplate): NpoClient =
        NpoClientImpl(WAREHOUSE_ID, testRestTemplate.restTemplate)

}

const val WAREHOUSE_ID = "1"
