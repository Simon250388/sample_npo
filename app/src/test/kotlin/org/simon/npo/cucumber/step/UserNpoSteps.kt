package org.simon.npo.cucumber.step

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions
import org.simon.npo.NpoClient
import org.simon.npo.cucumber.AbstractStepsDefinitions
import org.simon.npo.dto.UserNpoStartRequest
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

class UserNpoSteps(private val client: NpoClient) : AbstractStepsDefinitions() {
    @When("пользователь {string} начинает нпо {string}")
    fun startIndirectActivity(userName: String, npoType: String) =
        client.startNpoActivity(
            UserNpoStartRequest.builder()
                .withActivity(npoType)
                .withUserNames(setOf(userName))
                .build()
        ).let { verifyResponse(it) }


    @When("пользователю {string} назначают нпо {string} с текущего момента до {cucumberDate}")
    fun whenAssignerStartUserActivityFromNowToPlannedMoment(
        userName: String, npoType: String, plannedEndTime: LocalDateTime
    ) = client.startNpoActivity(
        UserNpoStartRequest.builder()
            .withActivity(npoType)
            .withUserNames(setOf(userName))
            .withPlannedEndTime(OffsetDateTime.of(plannedEndTime, ZoneOffset.UTC))
            .build()
    ).let { verifyResponse(it) }


    @When("пользователю {string} назначают нпо {string} с {cucumberDate} до {cucumberDate}")
    fun whenAssignerStartUserActivityToPlannedMoment(
        userName: String, npoType: String, startTime: LocalDateTime, plannedEndTime: LocalDateTime
    ) = client.startNpoActivity(
        UserNpoStartRequest.builder()
            .withActivity(npoType)
            .withUserNames(setOf(userName))
            .withPlannedEndTime(OffsetDateTime.of(plannedEndTime, ZoneOffset.UTC))
            .withStartTime(OffsetDateTime.of(startTime, ZoneOffset.UTC))
            .build()
    ).let { verifyResponse(it) }


    @Then("у пользователя {string} есть действующее нпо {string}")
    fun verifyNpoActivityActive(userName: String, npoType: String) {
        val response = client.isActiveNpoActivityActive(userName)
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.statusCode)
        Assertions.assertEquals(npoType, Objects.requireNonNull(response.body).activity)
    }

    @Then("у пользователя {string} нет действующих нпо")
    fun verifyNoActivityActive(userName: String) {
        val response = client.isActiveNpoActivityActive(userName)
        Assertions.assertEquals(HttpStatusCode.valueOf(204), response.statusCode)
    }

    private fun verifyResponse(npoStartResponse: ResponseEntity<Void>) {
        Assertions.assertTrue(
            npoStartResponse.statusCode.is2xxSuccessful,
            "Waiting response code is 2xx, but was ${npoStartResponse.statusCode}"
        )
        getContext().putApiResponse(npoStartResponse)
    }
}
