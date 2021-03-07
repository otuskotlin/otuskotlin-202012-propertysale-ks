package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.app
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomControllerTest {

    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Room List`() {
        val res = client
            .post()
            .uri("/room/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(PsRequestRoomList())
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseRoomList>()
            .returnResult()
            .responseBody

        assertEquals(7, res?.rooms?.size)
    }

    @Test
    fun `Room Create`() {
        val res = client
            .post()
            .uri("/room/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestRoomCreate(
                    createData = PsRoomCreateDto(
                        name = "Test Room",
                        description = "Test Room Description",
                        length = 5.0,
                        width = 3.0
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseRoomCreate>()
            .returnResult()
            .responseBody

        assertEquals("test-room-id", res?.room?.id)
        assertEquals("Test Room", res?.room?.name)
        assertEquals("Test Room Description", res?.room?.description)
        assertEquals(5.0, res?.room?.length)
        assertEquals(3.0, res?.room?.width)
    }

    @Test
    fun `Room Read`() {
        val res = client
            .post()
            .uri("/room/read")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestRoomRead(
                    roomId = "test-room-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseRoomRead>()
            .returnResult()
            .responseBody

        assertEquals("test-room-id", res?.room?.id)
        assertEquals("Room test-room-id", res?.room?.name)
        assertEquals("Description of room test-room-id", res?.room?.description)
        assertEquals(7.0, res?.room?.length)
        assertEquals(5.0, res?.room?.width)
    }

    @Test
    fun `Room Update`() {
        val res = client
            .post()
            .uri("/room/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestRoomUpdate(
                    updateData = PsRoomUpdateDto(
                        id = "test-room-id",
                        name = "Test Room updated",
                        description = "Test Room Description updated",
                        length = 4.0,
                        width = 4.0
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseRoomDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-room-id", res?.room?.id)
        assertEquals("Test Room updated", res?.room?.name)
        assertEquals("Test Room Description updated", res?.room?.description)
        assertEquals(4.0, res?.room?.length)
        assertEquals(4.0, res?.room?.width)
    }

    @Test
    fun `Room Delete`() {
        val res = client
            .post()
            .uri("/room/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestRoomDelete(
                    roomId = "test-room-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseRoomDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-room-id", res?.room?.id)
        assertTrue(res?.deleted!!)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
