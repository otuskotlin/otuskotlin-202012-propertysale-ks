package ru.otus.otuskotlin.propertysale.be.app.ktor

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ApplicationKtTest {

    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testGet() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, RestEndpoints.flatRead) {
                val body = PsRequestFlatRead(
                    requestId = "test-request-id",
                    flatId = "test-flat-id",
                    debug = PsRequestFlatRead.Debug(stubCase = PsRequestFlatRead.StubCase.SUCCESS)
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

                val res = (jsonConfig.decodeFromString(PsMessage.serializer(), jsonString) as? PsResponseFlatRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("test-request-id", res.onRequest)
                assertEquals("flat-test-name", res.flat?.name)
            }
        }
    }
}
