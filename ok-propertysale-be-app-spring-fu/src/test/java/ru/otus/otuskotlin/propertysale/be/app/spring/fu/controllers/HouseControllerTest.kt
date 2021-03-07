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
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseCreateDto
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HouseControllerTest {

    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `House List`() {
        val res = client
            .post()
            .uri("/house/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(PsRequestHouseList())
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseHouseList>()
            .returnResult()
            .responseBody

        assertEquals(7, res?.houses?.size)
    }

    @Test
    fun `House Create`() {
        val res = client
            .post()
            .uri("/house/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestHouseCreate(
                    createData = PsHouseCreateDto(
                        name = "Test House",
                        description = "Test House Description",
                        area = 250.0
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseHouseCreate>()
            .returnResult()
            .responseBody

        assertEquals("test-house-id", res?.house?.id)
        assertEquals("Test House", res?.house?.name)
        assertEquals("Test House Description", res?.house?.description)
        assertEquals(250.0, res?.house?.area)
    }

    @Test
    fun `House Read`() {
        val res = client
            .post()
            .uri("/house/read")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestHouseRead(
                    houseId = "test-house-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseHouseRead>()
            .returnResult()
            .responseBody

        assertEquals("test-house-id", res?.house?.id)
        assertEquals("House test-house-id", res?.house?.name)
        assertEquals("Description of house test-house-id", res?.house?.description)
        assertEquals(150.0, res?.house?.area)
    }

    @Test
    fun `House Update`() {
        val res = client
            .post()
            .uri("/house/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestHouseUpdate(
                    updateData = PsHouseUpdateDto(
                        id = "test-house-id",
                        name = "Test House updated",
                        description = "Test House Description updated",
                        area = 100.0
                    )
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseHouseDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-house-id", res?.house?.id)
        assertEquals("Test House updated", res?.house?.name)
        assertEquals("Test House Description updated", res?.house?.description)
        assertEquals(100.0, res?.house?.area)
    }

    @Test
    fun `House Delete`() {
        val res = client
            .post()
            .uri("/house/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(
                PsRequestHouseDelete(
                    houseId = "test-house-id",
                )
            )
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<PsResponseHouseDelete>()
            .returnResult()
            .responseBody

        assertEquals("test-house-id", res?.house?.id)
        assertTrue(res?.deleted!!)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }
}
