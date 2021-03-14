package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatUpdate

fun BePsContext.setQuery(request: PsRequestFlatRead) {
    this.requestFlatId = request.flatId?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE
}

fun BePsContext.setQuery(request: PsRequestFlatCreate) {
    request.createData?.let { data ->
        this.requestFlat = BePsFlatModel(
            name = data.name ?: "",
            description = data.description ?: "",
            floor = data.floor ?: Int.MIN_VALUE,
            numberOfRooms = data.numberOfRooms ?: Int.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestFlatUpdate) {
    request.updateData?.let { data ->
        this.requestFlat = BePsFlatModel(
            id = data.id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
            name = data.name ?: "",
            description = data.description ?: "",
            floor = data.floor ?: Int.MIN_VALUE,
            numberOfRooms = data.numberOfRooms ?: Int.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestFlatDelete) {
    this.requestFlatId = request.flatId?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE
}

fun BePsContext.respondFlatRead() = PsResponseFlatRead(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport()
)

fun BePsContext.respondFlatCreate() = PsResponseFlatCreate(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport()
)

fun BePsContext.respondFlatUpdate() = PsResponseFlatUpdate(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport()
)

fun BePsContext.respondFlatDelete() = PsResponseFlatDelete(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport()
)

fun BePsContext.respondFlatList() = PsResponseFlatList(
    flats = responseFlats.takeIf { it.isNotEmpty() }?.filter { it != BePsFlatModel.NONE }
        ?.map { it.toTransport() }
)

internal fun BePsFlatModel.toTransport() = PsFlatDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    floor = floor.takeIf { it != Int.MIN_VALUE },
    numberOfRooms = numberOfRooms.takeIf { it != Int.MIN_VALUE },
    actions = actions.takeIf { it.isNotEmpty() }
        ?.filter { it != BePsActionModel.NONE }?.map { it.toTransport() }?.toSet()
)

fun PsFlatDto.toInternal() = BePsFlatModel(
    id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    floor = floor ?: Int.MIN_VALUE,
    numberOfRooms = numberOfRooms ?: Int.MIN_VALUE,
    actions = actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)
