package ru.otus.otuskotlin.propertysale.be.app.ktor.validaton.flat

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class FlatCreateValidationTest {

    @Test
    fun `non-empty create must success`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.flatCreate) {
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

                val bodyString = jsonConfig.encodeToString(PsMessage.serializer(), body)
                println("REQUEST JSON: $bodyString")
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertEquals("flat-test-name", res.flat?.name)
            }
        }
    }

    @Test
    fun `empty title or description must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.flatCreate) {
                val body = PsRequestFlatCreate(
                    requestId = "test-request-id",
                    createData = PsFlatCreateDto(
                    )
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

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatCreate)
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
            handleRequest(HttpMethod.Post, RestEndpoints.flatCreate) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
