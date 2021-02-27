package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsDetailIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsDetailModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsParamIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsParamModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsUnitTypeIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsUnitTypeModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.crud.PsRequestCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.crud.PsRequestRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsDetailDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsParamDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsUnitTypeDto

fun BePsContext.setQuery(request: IPsRequest) =
    when (request) {
        is PsRequestRead -> this.setQuery(request)
        is PsRequestCreate -> setQuery(request)
        else -> null
    }

fun BePsContext.setQuery(request: PsRequestRead) {
    this.requestId = request.id?.let { BePsIdModel(it) } ?: BePsIdModel.NONE
}

fun BePsContext.setQuery(request: PsRequestCreate) {
    request.createData?.let { data ->
        this.request = BePsModel(
            title = data.title ?: "",
            description = data.description ?: "",
            tags = data.tags?.toMutableSet() ?: mutableSetOf(),
            details = data.details?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf(),
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

private fun PsActionDto.toInternal() = BePsActionModel(
    id = id?.let { BePsActionIdModel(it) } ?: BePsActionIdModel.NONE,
    type = type ?: "",
    content = content ?: "",
    status = status ?: ""
)

fun PsUnitTypeDto.toInternal() = BePsUnitTypeModel(
    id = id?.let { BePsUnitTypeIdModel(it) } ?: BePsUnitTypeIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    synonyms = synonyms?.toMutableSet() ?: mutableSetOf(),
    symbol = symbol ?: "",
    isBase = isBase ?: false,
)

fun PsDetailDto.toInternal() = BePsDetailModel(
    id = id?.let { BePsDetailIdModel(it) } ?: BePsDetailIdModel.NONE,
    param = param?.toInternal() ?: BePsParamModel.NONE,
)

fun PsParamDto.toInternal() = BePsParamModel(
    id = id?.let { BePsParamIdModel(it) } ?: BePsParamIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    priority = priority ?: Double.MIN_VALUE,
    units = units?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)

fun PsDto.toInternal() = BePsModel(
    id = id?.let { BePsIdModel(it) } ?: BePsIdModel.NONE,
    title = title ?: "",
    description = description ?: "",
    tags = tags?.toMutableSet() ?: mutableSetOf(),
    details = details?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf(),
)
