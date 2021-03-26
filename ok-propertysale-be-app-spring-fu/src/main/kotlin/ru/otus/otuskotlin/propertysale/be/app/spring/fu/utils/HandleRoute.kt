package ru.otus.otuskotlin.propertysale.be.app.spring.fu.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
inline fun <reified T : PsMessage, reified U : PsMessage> handleRoute(
    request: ServerRequest,
    crossinline block: suspend BePsContext.(T?) -> U
): ServerResponse = runBlocking {
    val ctx = BePsContext(
        responseId = UUID.randomUUID().toString(),
        timeStarted = Instant.now()
    )
    try {
        val queryJson = request.body<String>()
        val query = jsonConfig.decodeFromString(T::class.serializer(), queryJson) as T
        ctx.status = BePsContextStatus.RUNNING
        val response = ctx.block(query)
        val responseJson = jsonConfig.encodeToString(PsMessage.serializer(), response)
        ServerResponse.ok().body(responseJson)
    } catch (e: Throwable) {
        ctx.status = BePsContextStatus.FAILING
        ctx.errors.add(e.toModel())
        val response = ctx.block(null)
        val responseJson = jsonConfig.encodeToString(U::class.serializer(), response)
        ServerResponse.ok().body(responseJson)
    }
}
