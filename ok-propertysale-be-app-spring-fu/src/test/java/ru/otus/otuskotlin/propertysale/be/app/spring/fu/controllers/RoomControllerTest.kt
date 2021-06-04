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
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models.PsRoomCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models.PsRoomListFilterDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models.PsRoomUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomUpdate
import kotlin.test.fail

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomControllerTest {

    private val client = WebTestClient
        .bindToServer().baseUrl("http://localhost:8181")
        .build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Room List`() {
        val body = PsRequestRoomList(
            requestId = "test-request-id",
            filterData = PsRoomListFilterDto(

            ),
            debug = PsRequestRoomList.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestRoomList.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/room/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomList)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("room-test-name", res.rooms?.firstOrNull()?.name)
    }

    @Test
    fun `Room Create`() {
        val body = PsRequestRoomCreate(
            requestId = "test-request-id",
            createData = PsRoomCreateDto(
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = setOf(
                    PsActionDto("test-action-1"),
                    PsActionDto("test-action-2"),
                    PsActionDto("test-action-3")
                )
            ),
            debug = PsRequestRoomCreate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestRoomCreate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/room/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomCreate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("room-test-name", res.room?.name)
    }

    @Test
    fun `Room Read`() {
        val body = PsRequestRoomRead(
            requestId = "test-request-id",
            roomId = "test-room-id",
            debug = PsRequestRoomRead.Debug(stubCase = PsRequestRoomRead.StubCase.SUCCESS)
        )

        val jsonString = client
            .post()
            .uri(RestEndpoints.roomRead)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomRead)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("room-test-name", res.room?.name)
    }

    @Test
    fun `Room Update`() {
        val body = PsRequestRoomUpdate(
            requestId = "test-request-id",
            updateData = PsRoomUpdateDto(
                id = "room-test-id",
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = setOf(
                    PsActionDto("test-action-1"),
                    PsActionDto("test-action-2"),
                    PsActionDto("test-action-3")
                )
            ),
            debug = PsRequestRoomUpdate.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestRoomUpdate.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/room/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomUpdate)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("room-test-id", res.room?.id)
        kotlin.test.assertEquals("room-test-name", res.room?.name)
    }

    @Test
    fun `Room Delete`() {
        val body = PsRequestRoomDelete(
            requestId = "test-request-id",
            roomId = "test-room-id",
            debug = PsRequestRoomDelete.Debug(
                mode = PsWorkModeDto.TEST,
                stubCase = PsRequestRoomDelete.StubCase.SUCCESS
            )
        )

        val jsonString = client
            .post()
            .uri("/room/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(body)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>()
            .returnResult()
            .responseBody ?: fail("Null response json")

        println(jsonString)

        val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomDelete)
            ?: fail("Incorrect response format")

        kotlin.test.assertEquals(ResponseStatusDto.SUCCESS, res.status)
        kotlin.test.assertEquals("test-request-id", res.onRequest)
        kotlin.test.assertEquals("room-test-id", res.room?.id)
        kotlin.test.assertEquals("room-test-name", res.room?.name)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
