package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate

fun BePsContext.setQuery(request: IPsRequest) =
    when (request) {
        is PsRequestFlatRead -> setQuery(request)
        is PsRequestFlatCreate -> setQuery(request)
        is PsRequestFlatUpdate -> setQuery(request)
        is PsRequestFlatDelete -> setQuery(request)
        is PsRequestHouseRead -> setQuery(request)
        is PsRequestHouseCreate -> setQuery(request)
        is PsRequestHouseUpdate -> setQuery(request)
        is PsRequestHouseDelete -> setQuery(request)
        is PsRequestRoomRead -> setQuery(request)
        is PsRequestRoomCreate -> setQuery(request)
        is PsRequestRoomUpdate -> setQuery(request)
        is PsRequestRoomDelete -> setQuery(request)
        else -> null
    }

fun PsActionDto.toInternal() = BePsActionModel(
    id = id?.let { BePsActionIdModel(it) } ?: BePsActionIdModel.NONE,
    type = type ?: "",
    content = content ?: "",
    status = status ?: "",
)
