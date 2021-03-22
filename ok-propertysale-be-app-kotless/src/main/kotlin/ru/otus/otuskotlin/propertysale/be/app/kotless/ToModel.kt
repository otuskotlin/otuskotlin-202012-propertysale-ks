package ru.otus.otuskotlin.propertysale.be.app.kotless

import kotlinx.serialization.SerializationException
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError

private val logger = LoggerFactory.getLogger("Throwable.toModel")

fun Throwable.toModel(): PsError = when (this) {
    is SerializationException -> PsError(message = "Request JSON syntax error: ${this.message}")
    is ClassCastException -> PsError(message = "Wrong data sent to the endpoint: ${this.message}")
    else -> {
        logger.error("Unknown exception", this)
        PsError(message = "Some exception is thrown: ${this.message}")
    }
}
