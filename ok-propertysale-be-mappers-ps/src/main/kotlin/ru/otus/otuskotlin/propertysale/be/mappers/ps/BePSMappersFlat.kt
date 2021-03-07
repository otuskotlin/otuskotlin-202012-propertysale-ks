package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate

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

fun PsFlatDto.toInternal() = BePsFlatModel(
    id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    floor = floor ?: Int.MIN_VALUE,
    numberOfRooms = numberOfRooms ?: Int.MIN_VALUE,
    actions = actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)
