package ru.otus.otuskotlin.propertysale.be.app.ktor.validaton.room

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models.PsRoomUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomUpdate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class RoomUpdateValidationTest {

    @Test
    fun `non-empty update must success`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.roomUpdate) {
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
                            PsActionDto("test-action-2")
                        )
                    ),
                    debug = PsRequestRoomUpdate.Debug(
                        mode = PsWorkModeDto.TEST,
                        stubCase = PsRequestRoomUpdate.StubCase.SUCCESS
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

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertEquals("room-test-id", res.room?.id)
                assertEquals("room-test-name", res.room?.name)
            }
        }
    }

    @Test
    fun `empty id or title or description must fail`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.roomUpdate) {
                val body = PsRequestRoomUpdate(
                    requestId = "test-request-id",
                    updateData = PsRoomUpdateDto()
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

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomUpdate)
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
            handleRequest(HttpMethod.Post, RestEndpoints.roomUpdate) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseRoomUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
