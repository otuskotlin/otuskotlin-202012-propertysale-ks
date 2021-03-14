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
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class FlatControllerTest {

    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Flat List`() {
        val res = client
            .post()
            .uri("/flat/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(PsRequestFlatList())
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseFlatList>()
            .returnResult()
            .responseBody

        assertEquals(7, res?.flats?.size)
    }

    @Test
    fun `Flat Create`() {
        val res = client
            .post()
            .uri("/flat/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestFlatCreate(
                    createData = PsFlatCreateDto(
                        name = "Test Flat",
                        description = "Test Flat Description",
                        floor = 7,
                        numberOfRooms = 3
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseFlatCreate>()
            .returnResult()
            .responseBody

        assertEquals("test-flat-id", res?.flat?.id)
        assertEquals("Test Flat", res?.flat?.name)
        assertEquals("Test Flat Description", res?.flat?.description)
        assertEquals(7, res?.flat?.floor)
        assertEquals(3, res?.flat?.numberOfRooms)
    }

    @Test
    fun `Flat Read`() {
        val res = client
            .post()
            .uri("/flat/read")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestFlatRead(
                    flatId = "test-flat-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseFlatRead>()
            .returnResult()
            .responseBody

        assertEquals("test-flat-id", res?.flat?.id)
        assertEquals("Flat test-flat-id", res?.flat?.name)
        assertEquals("Description of flat test-flat-id", res?.flat?.description)
        assertEquals(5, res?.flat?.floor)
        assertEquals(2, res?.flat?.numberOfRooms)
    }

    @Test
    fun `Flat Update`() {
        val res = client
            .post()
            .uri("/flat/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestFlatUpdate(
                    updateData = PsFlatUpdateDto(
                        id = "test-flat-id",
                        name = "Test Flat updated",
                        description = "Test Flat Description updated",
                        floor = 8,
                        numberOfRooms = 4
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseFlatDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-flat-id", res?.flat?.id)
        assertEquals("Test Flat updated", res?.flat?.name)
        assertEquals("Test Flat Description updated", res?.flat?.description)
        assertEquals(8, res?.flat?.floor)
        assertEquals(4, res?.flat?.numberOfRooms)
    }

    @Test
    fun `Flat Delete`() {
        val res = client
            .post()
            .uri("/flat/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestFlatDelete(
                    flatId = "test-flat-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseFlatDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-flat-id", res?.flat?.id)
        assertTrue(res?.deleted!!)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
