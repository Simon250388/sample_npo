package org.simon.npo.cucumber.step

import io.cucumber.java.en.Given
import org.simon.npo.core.service.AppDateTimeProvider
import org.simon.npo.cucumber.AbstractStepsDefinitions
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

class CommonSteps(private val appDateTimeProvider: AppDateTimeProvider) : AbstractStepsDefinitions() {
    @Given("сейчас {cucumberDate}")
    fun givenNow(dateTime: LocalDateTime): Unit =
        run {
            Clock.fixed(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
                .also { appDateTimeProvider.clock = it }
        }
}
