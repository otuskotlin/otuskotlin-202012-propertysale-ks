package ru.otus.otuskotlin.propertysale.be.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.modules.commonModule
import ru.otus.otuskotlin.propertysale.be.app.ktor.modules.flatModule
import ru.otus.otuskotlin.propertysale.be.app.ktor.modules.houseModule
import ru.otus.otuskotlin.propertysale.be.app.ktor.modules.roomModule
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import kotlin.test.assertEquals
import kotlin.test.fail

internal class PsBeAppKtorKtTest {

    @Test
    fun testRoot() {
        withTestApplication({ commonModule(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testFlatRead() {
        withTestApplication({
            commonModule(testing = true)
            flatModule(testing = true)
        }) {
            handleRequest(HttpMethod.Post, "/flat/read") {
                val body = PsRequestFlatRead(
                    requestId = "test-flat-read-request-id",
                    flatId = "test-flat-id",
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(PsRequestFlatRead.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val result = jsonConfig.decodeFromString(PsResponseFlatRead.serializer(), jsonString)

                assertEquals(ResponseStatusDto.SUCCESS, result.status)
                assertEquals("test-flat-read-request-id", result.onRequest)
                assertEquals("flat-name", result.flat?.name)
            }
        }
    }
    @Test
    fun testHouseRead() {
        withTestApplication({
            commonModule(testing = true)
            houseModule(testing = true)
        }) {
            handleRequest(HttpMethod.Post, "/house/read") {
                val body = PsRequestHouseRead(
                    requestId = "test-house-read-request-id",
                    houseId = "test-house-id",
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(PsRequestHouseRead.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val result = jsonConfig.decodeFromString(PsResponseHouseRead.serializer(), jsonString)

                assertEquals(ResponseStatusDto.SUCCESS, result.status)
                assertEquals("test-house-read-request-id", result.onRequest)
                assertEquals("house-name", result.house?.name)
            }
        }
    }

    @Test
    fun testRoomRead() {
        withTestApplication({
            commonModule(testing = true)
            roomModule(testing = true)
        }) {
            handleRequest(HttpMethod.Post, "/room/read") {
                val body = PsRequestRoomRead(
                    requestId = "test-room-read-request-id",
                    roomId = "test-room-id",
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(PsRequestRoomRead.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val result = jsonConfig.decodeFromString(PsResponseRoomRead.serializer(), jsonString)

                assertEquals(ResponseStatusDto.SUCCESS, result.status)
                assertEquals("test-room-read-request-id", result.onRequest)
                assertEquals("room-name", result.room?.name)
            }
        }
    }
}
