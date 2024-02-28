package org.simon.npo.cucumber.config

import io.cucumber.spring.CucumberContextConfiguration
import org.simon.npo.NpoApplication
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [NpoApplication::class])
class CucumberSpringConfiguration
