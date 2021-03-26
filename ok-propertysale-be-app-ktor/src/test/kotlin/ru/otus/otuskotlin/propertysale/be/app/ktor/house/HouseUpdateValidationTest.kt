package ru.otus.otuskotlin.propertysale.be.app.ktor.house

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class HouseUpdateValidationTest {

    @Test
    fun `non-empty update must success`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseUpdate) {
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

                val bodyString = jsonConfig.encodeToString(PsMessage.serializer(), body)
                println("REQUEST JSON: $bodyString")
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertEquals("house-test-id", res.house?.id)
                assertEquals("house-test-name", res.house?.name)
            }
        }
    }

    @Test
    fun `empty id or title or description must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseUpdate) {
                val body = PsRequestHouseUpdate(
                    requestId = "test-request-id",
                    updateData = PsHouseUpdateDto()
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

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertTrue {
                    res.errors?.firstOrNull {
                        it.message?.contains("name") == true
                            && it.message?.contains("empty") == true
                    } != null
                }
                assertTrue {
                    res.errors?.firstOrNull {
                        it.message?.contains("description") == true
                            && it.message?.contains("empty") == true
                    } != null
                }
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.houseUpdate) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseHouseUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
