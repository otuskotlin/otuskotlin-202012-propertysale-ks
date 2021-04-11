package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import kotlinx.coroutines.withTimeoutOrNull
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WebsocketTest {

    @Test
    fun flatListTest() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocketConversation("/ws") { incoming, outgoing ->
                val query = PsRequestFlatList(
                    requestId = "testFlatId",
                    debug = PsRequestFlatList.Debug(
                        stubCase = PsRequestFlatList.StubCase.SUCCESS
                    )
                )
                withTimeoutOrNull(250L) {
                    while (true) {
                        val respJson = (incoming.receive() as Frame.Text).readText()
                        println("GOT INIT RESPONSE: $respJson")
                    }
                }
                val requestJson = jsonConfig.encodeToString(PsMessage.serializer(), query)
                outgoing.send(Frame.Text(requestJson))
                val respJson = (incoming.receive() as Frame.Text).readText()
                println("RESPONSE: $respJson")
                val response = jsonConfig.decodeFromString(PsMessage.serializer(), respJson) as PsResponseFlatList
                assertEquals("testFlatId", response.onRequest)

            }
        }
    }
    @Test
    fun flatListErrorTest() {
        withTestApplication({ module(testing = true) }) {
            handleWebSocketConversation("/ws") { incoming, outgoing ->
                withTimeoutOrNull(250L) {
                    while (true) {
                        val respJson = (incoming.receive() as Frame.Text).readText()
                        println("GOT INIT RESPONSE: $respJson")
                    }
                }
                val requestJson = """{"type":"123"}"""
                outgoing.send(Frame.Text(requestJson))
                val respJson = (incoming.receive() as Frame.Text).readText()
                println("RESPONSE: $respJson")
                val response = jsonConfig.decodeFromString(PsMessage.serializer(), respJson) as PsResponseFlatList
                assertEquals(ResponseStatusDto.BAD_REQUEST, response.status)

            }
        }
    }
}
