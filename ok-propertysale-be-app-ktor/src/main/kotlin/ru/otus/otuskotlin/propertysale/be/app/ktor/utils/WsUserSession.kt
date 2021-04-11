package ru.otus.otuskotlin.propertysale.be.app.ktor.utils

import io.ktor.http.cio.websocket.*
import ru.otus.otuskotlin.propertysale.be.common.repositories.IUserSession

class WsUserSession(
    override val fwSession: WebSocketSession
) : IUserSession<WebSocketSession> {
}
