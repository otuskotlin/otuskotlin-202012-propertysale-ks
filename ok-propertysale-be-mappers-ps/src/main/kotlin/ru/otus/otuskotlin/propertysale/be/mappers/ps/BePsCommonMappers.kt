package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.IPsError
import ru.otus.otuskotlin.propertysale.be.mappers.ps.exceptions.WrongBePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto

fun <T : IPsRequest> BePsContext.setQuery(query: T, block: BePsContext.() -> Unit) = apply {
    onRequest = query.requestId ?: ""
    block()
}

fun IPsError.toTransport() = ErrorDto(
    message = message
)

fun BePsContextStatus.toTransport(): ResponseStatusDto = when (this) {
    BePsContextStatus.NONE -> throw WrongBePsContextStatus(this)
    BePsContextStatus.RUNNING -> throw WrongBePsContextStatus(this)
    BePsContextStatus.FINISHING -> ResponseStatusDto.SUCCESS
    BePsContextStatus.FAILING -> throw WrongBePsContextStatus(this)
    BePsContextStatus.SUCCESS -> ResponseStatusDto.SUCCESS
    BePsContextStatus.ERROR -> ResponseStatusDto.BAD_REQUEST
}

internal fun BePsActionModel.toTransport() = PsActionDto(
    id = id.id.takeIf { it.isNotBlank() },
    type = type.takeIf { it.isNotBlank() },
    content = content.takeIf { it.isNotBlank() },
    status = status.takeIf { it.isNotBlank() }
)

internal fun PsActionDto.toModel() = BePsActionModel(
    id = id?.let { BePsActionIdModel(it) } ?: BePsActionIdModel.NONE,
    type = type ?: "",
    content = content ?: "",
    status = status ?: "",
)
