package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.PsRoomDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomUpdate

fun BePsContext.setQuery(request: PsRequestRoomRead) {
    this.requestRoomId = request.roomId?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE
}

fun BePsContext.setQuery(request: PsRequestRoomCreate) {
    request.createData?.let { data ->
        this.requestRoom = BePsRoomModel(
            name = data.name ?: "",
            description = data.description ?: "",
            length = data.length ?: Double.MIN_VALUE,
            width = data.width ?: Double.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestRoomUpdate) {
    request.updateData?.let { data ->
        this.requestRoom = BePsRoomModel(
            id = data.id?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE,
            name = data.name ?: "",
            description = data.description ?: "",
            length = data.length ?: Double.MIN_VALUE,
            width = data.width ?: Double.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestRoomDelete) {
    this.requestRoomId = request.roomId?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE
}

fun BePsContext.respondRoomRead() = PsResponseRoomRead(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport()
)

fun BePsContext.respondRoomCreate() = PsResponseRoomCreate(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport()
)

fun BePsContext.respondRoomUpdate() = PsResponseRoomUpdate(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport()
)

fun BePsContext.respondRoomDelete() = PsResponseRoomDelete(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport()
)

fun BePsContext.respondRoomList() = PsResponseRoomList(
    rooms = responseRooms.takeIf { it.isNotEmpty() }?.filter { it != BePsRoomModel.NONE }
        ?.map { it.toTransport() }
)

internal fun BePsRoomModel.toTransport() = PsRoomDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    length = length.takeIf { it != Double.MIN_VALUE },
    width = width.takeIf { it != Double.MIN_VALUE },
    actions = actions.takeIf { it.isNotEmpty() }
        ?.filter { it != BePsActionModel.NONE }?.map { it.toTransport() }?.toSet()
)

fun PsRoomDto.toInternal() = BePsRoomModel(
    id = id?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    length = length ?: Double.MIN_VALUE,
    width = width ?: Double.MIN_VALUE,
    actions = actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)
