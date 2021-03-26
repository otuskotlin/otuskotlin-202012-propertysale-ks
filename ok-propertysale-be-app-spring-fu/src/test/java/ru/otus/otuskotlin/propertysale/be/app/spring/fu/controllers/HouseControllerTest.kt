package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.app
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.config.jsonConfig
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseListFilterDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HouseControllerTest {

    private val client = WebTestClient
        .bindToServer().baseUrl("http://localhost:8181")
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `House List`() {
        val body = PsRequestHouseList(
            requestId = "test-request-id",
            filterData = PsHouseListFilterDto(

            ),
            debug = PsRequestHouseList.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestHouseList.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/house/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseList)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("house-test-name", res.houses?.firstOrNull()?.name)
    }

    @Test
    fun `House Create`() {
        val body = PsRequestHouseCreate(
            requestId = "test-request-id",
            createData = PsHouseCreateDto(
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = setOf(
                    PsActionDto("test-action-1")
                )
            ),
            debug = PsRequestHouseCreate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestHouseCreate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/house/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseCreate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("house-test-name", res.house?.name)
    }

    @Test
    fun `House Read`() {
        val body = PsRequestHouseRead(
            requestId = "test-request-id",
            houseId = "test-house-id",
            debug = PsRequestHouseRead.Debug(stubCase = PsRequestHouseRead.StubCase.SUCCESS)
        )

        val jsonString = client
            .post()
            .uri(RestEndpoints.houseRead)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseRead)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("house-test-name", res.house?.name)
    }

    @Test
    fun `House Update`() {
        val body = PsRequestHouseUpdate(
            requestId = "test-request-id",
            updateData = PsHouseUpdateDto(
                id = "house-test-id",
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = setOf(
                    PsActionDto("test-action-1")
                )
            ),
            debug = PsRequestHouseUpdate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestHouseUpdate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/house/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseUpdate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("house-test-id", res.house?.id)
        kotlin.test.assertEquals("house-test-name", res.house?.name)
    }

    @Test
    fun `House Delete`() {
        val body = PsRequestHouseDelete(
            requestId = "test-request-id",
            houseId = "test-house-id",
            debug = PsRequestHouseDelete.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestHouseDelete.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/house/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseDelete)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("house-test-id", res.house?.id)
        kotlin.test.assertEquals("house-test-name", res.house?.name)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
