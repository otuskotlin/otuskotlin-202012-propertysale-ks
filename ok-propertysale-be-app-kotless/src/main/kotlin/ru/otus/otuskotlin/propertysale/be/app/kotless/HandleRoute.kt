package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.KotlessContext
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import java.time.Instant
import java.util.*

val log: Logger = LoggerFactory.getLogger("HandleRoute")

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any, reified R : Any> handle(crossinline block: suspend BePsContext.(T?) -> R): String? =
    KotlessContext.HTTP.request
        .myBody
        ?.let { q ->
            runBlocking {
                log.debug("Handling query: {}", q)
                val ctx = BePsContext(
                    responseId = UUID.randomUUID().toString(),
                    timeStarted = Instant.now()
                )
                try {
                    val query = Json.decodeFromString(T::class.serializer(), q)
                    ctx.status = BePsContextStatus.RUNNING
                    val result = ctx.block(query)
                    Json.encodeToString(R::class.serializer(), result).also { r ->
                        log.debug("Sending response: {}", r)
                    }
                } catch (e: Throwable) {
                    ctx.status = BePsContextStatus.FAILING
                    ctx.errors.add(e.toModel())
                    val result = ctx.block(null)
                    Json.encodeToString(R::class.serializer(), result).also { r ->
                        log.debug("Sending response: {}", r)
                    }
                }
            }
        }
