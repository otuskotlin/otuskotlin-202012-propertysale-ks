package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.WsUserSession
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.service
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.toModel
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.repositories.EmptyUserSession
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import java.time.Instant
import java.util.*

private val sessions = mutableMapOf<WebSocketSession, WsUserSession>()

fun Application.websocketEndpoints(
    flatService: FlatService,
    houseService: HouseService,
    roomService: RoomService,
) {
    install(WebSockets)

    routing {
        mpWebsocket(flatService, houseService, roomService)
    }
}

@OptIn(InternalSerializationApi::class)
fun Routing.mpWebsocket(
    flatService: FlatService,
    houseService: HouseService,
    roomService: RoomService
) {
    webSocket("/ws") { // websocketSession
        sessions[this] = WsUserSession(fwSession = this)
        apply {
            val ctx = BePsContext(
                responseId = UUID.randomUUID().toString(),
                timeStarted = Instant.now(),
                userSession = sessions[this] ?: EmptyUserSession,
                status = BePsContextStatus.RUNNING
            )
            service(
                context = ctx,
                query = null,
                flatService = flatService,
                houseService = houseService,
                roomService = roomService
            )?.also {
                val respJson = jsonConfig.encodeToString(PsMessage::class.serializer(), it)
                outgoing.send(Frame.Text(respJson))
            }
        }

        for (frame in incoming) {
            if (frame is Frame.Text) {
                val ctx = BePsContext(
                    responseId = UUID.randomUUID().toString(),
                    timeStarted = Instant.now(),
                    userSession = sessions[this] ?: EmptyUserSession
                )
                try {
                    val requestJson = frame.readText()
                    val query = jsonConfig.decodeFromString(PsMessage.serializer(), requestJson)
                    ctx.status = BePsContextStatus.RUNNING
                    service(
                        context = ctx,
                        query = query,
                        flatService = flatService,
                        houseService = houseService,
                        roomService = roomService
                    )?.also {
                        val respJson = jsonConfig.encodeToString(PsMessage::class.serializer(), it)
                        outgoing.send(Frame.Text(respJson))
                    }
                } catch (e: ClosedReceiveChannelException) {
                    service(
                        context = ctx,
                        query = null,
                        flatService = flatService,
                        houseService = houseService,
                        roomService = roomService
                    )
                    sessions -= this
                } catch (e: Throwable) {
                    e.printStackTrace()
                    ctx.status = BePsContextStatus.FAILING
                    ctx.errors.add(e.toModel())
                    service(
                        context = ctx,
                        query = null,
                        flatService = flatService,
                        houseService = houseService,
                        roomService = roomService
                    )?.also {
                        val respJson = jsonConfig.encodeToString(PsMessage::class.serializer(), it)
                        outgoing.send(Frame.Text(respJson))
                    }
                }

                val responseJson = ""
//                    if (text.equals("bye", ignoreCase = true)) {
//                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//                    }
            }
        }
    }
}
