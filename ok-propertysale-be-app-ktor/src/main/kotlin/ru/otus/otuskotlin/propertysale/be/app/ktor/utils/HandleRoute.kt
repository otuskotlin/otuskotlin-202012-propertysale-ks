package ru.otus.otuskotlin.propertysale.be.app.ktor.utils

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.logging.PsLogContext
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified T : IPsRequest, reified U : PsMessage> PipelineContext<Unit, ApplicationCall>.handleRoute(
    logId: String,
    logger: PsLogContext,
    crossinline block: suspend BePsContext.(T?) -> U
) {
    val ctx = BePsContext(
        timeStarted = Instant.now(),
        responseId = UUID.randomUUID().toString()
    )
    try {
        logger.doWithLoggingSusp(logId) {
            val query = call.receive<PsMessage>() as T
            ctx.status = BePsContextStatus.RUNNING
            val response = ctx.block(query)
            val respJson = jsonConfig.encodeToString(PsMessage::class.serializer(), response)
            call.respondText(respJson, contentType = ContentType.parse("application/json"))
        }
    } catch (e: Throwable) {
        ctx.status = BePsContextStatus.FAILING
        ctx.errors.add(e.toModel())
        val response = ctx.block(null)
        val respJson = jsonConfig.encodeToString(PsMessage::class.serializer(), response)
        call.respondText(respJson, contentType = ContentType.parse("application/json"))
    }
}
