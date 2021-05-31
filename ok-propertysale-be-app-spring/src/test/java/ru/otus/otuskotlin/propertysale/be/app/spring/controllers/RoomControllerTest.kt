package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models.PsRoomCreateDto
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

@WebFluxTest
internal class RoomControllerTest(@Autowired val client: WebTestClient) {

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

        assertEquals("room-id-created", res?.room?.id)
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
}
