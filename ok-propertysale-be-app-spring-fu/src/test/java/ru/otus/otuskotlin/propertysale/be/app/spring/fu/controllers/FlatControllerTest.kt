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
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatListFilterDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatUpdate
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class FlatControllerTest {

    private val client = WebTestClient
        .bindToServer().baseUrl("http://localhost:8181")
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Flat List`() {
        val body = PsRequestFlatList(
            requestId = "test-request-id",
            filterData = PsFlatListFilterDto(

            ),
            debug = PsRequestFlatList.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestFlatList.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/flat/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatList)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("flat-test-name", res.flats?.firstOrNull()?.name)
    }

    @Test
    fun `Flat Create`() {
        val body = PsRequestFlatCreate(
            requestId = "test-request-id",
            createData = PsFlatCreateDto(
                name = "flat-test-name",
                description = "flat-test-description",
                floor = 5,
                numberOfRooms = 2,
                actions = setOf(
                    PsActionDto("test-action-1"),
                    PsActionDto("test-action-2"),
                    PsActionDto("test-action-3")
                )
            ),
            debug = PsRequestFlatCreate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestFlatCreate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/flat/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatCreate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("flat-test-name", res.flat?.name)
    }

    @Test
    fun `Flat Read`() {
        val body = PsRequestFlatRead(
            requestId = "test-request-id",
            flatId = "test-flat-id",
            debug = PsRequestFlatRead.Debug(stubCase = PsRequestFlatRead.StubCase.SUCCESS)
        )

        val jsonString = client
            .post()
            .uri(RestEndpoints.flatRead)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatRead)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("flat-test-name", res.flat?.name)
    }

    @Test
    fun `Flat Update`() {
        val body = PsRequestFlatUpdate(
            requestId = "test-request-id",
            updateData = PsFlatUpdateDto(
                id = "flat-test-id",
                name = "flat-test-name",
                description = "flat-test-description",
                floor = 5,
                numberOfRooms = 2,
                actions = setOf(
                    PsActionDto("test-action-1"),
                    PsActionDto("test-action-2"),
                    PsActionDto("test-action-3")
                )
            ),
            debug = PsRequestFlatUpdate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestFlatUpdate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/flat/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatUpdate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("flat-test-id", res.flat?.id)
        kotlin.test.assertEquals("flat-test-name", res.flat?.name)
    }

    @Test
    fun `Flat Delete`() {
        val body = PsRequestFlatDelete(
            requestId = "test-request-id",
            flatId = "test-flat-id",
            debug = PsRequestFlatDelete.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestFlatDelete.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/flat/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatDelete)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("flat-test-id", res.flat?.id)
        kotlin.test.assertEquals("flat-test-name", res.flat?.name)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
