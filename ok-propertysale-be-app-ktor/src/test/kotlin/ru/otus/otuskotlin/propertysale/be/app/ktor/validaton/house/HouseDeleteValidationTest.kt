package ru.otus.otuskotlin.propertysale.be.app.ktor.validaton.house

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseDelete
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class HouseDeleteValidationTest {

    @Test
    fun `non-empty delete must success`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseDelete) {
                val body = PsRequestHouseDelete(
                    requestId = "test-request-id",
                    houseId = "test-house-id",
                    debug = PsRequestHouseDelete.Debug(
                        mode = PsWorkModeDto.TEST,
                        stubCase = PsRequestHouseDelete.StubCase.SUCCESS
                    )
                )

                val bodyString = jsonConfig.encodeToString(PsMessage.serializer(), body)
                println("REQUEST JSON: $bodyString")
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertEquals("house-test-id", res.house?.id)
                assertEquals("house-test-name", res.house?.name)
            }
        }
    }

    @Test
    fun `empty house id must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseDelete) {
                val body = PsRequestHouseDelete(
                    requestId = "test-request-id",
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(PsMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertTrue("errors: ${res.errors}") {
                    res.errors?.firstOrNull {
                        it.message?.toLowerCase()?.contains("id") == true
                            && it.message?.toLowerCase()?.contains("empty") == true
                    } != null
                }
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseDelete) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
