package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.KotlessContext
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("PsBeAppKotlessKt")

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any, reified R : Any> handle(crossinline block: suspend (T) -> R): String? =
    KotlessContext
        .HTTP
        .request
        .myBody
        ?.let { q ->
            runBlocking {
                log.debug("Handling query: {}", q)
                val query = Json.decodeFromString(T::class.serializer(), q)
                val result = block(query)
                Json.encodeToString(R::class.serializer(), result).also { r ->
                    log.debug("Sending response: {}", r)
                }
            }
        }
