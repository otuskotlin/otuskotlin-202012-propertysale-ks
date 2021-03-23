package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.PsRoomDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomUpdate
import java.time.Instant

fun BePsContext.setQuery(query: PsRequestRoomRead) = setQuery(query) {
    requestRoomId = query.roomId?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestRoomRead.StubCase.SUCCESS -> PsStubCase.ROOM_READ_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestRoomCreate) = setQuery(query) {
    requestRoom = query.createData?.toModel() ?: BePsRoomModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestRoomCreate.StubCase.SUCCESS -> PsStubCase.ROOM_CREATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestRoomUpdate) = setQuery(query) {
    requestRoom = query.updateData?.toModel() ?: BePsRoomModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestRoomUpdate.StubCase.SUCCESS -> PsStubCase.ROOM_UPDATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestRoomDelete) = setQuery(query) {
    requestRoomId = query.roomId?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestRoomDelete.StubCase.SUCCESS -> PsStubCase.ROOM_DELETE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestRoomList) = setQuery(query) {
    roomFilter = query.filterData?.let {
        BePsRoomFilterModel(
            text = it.text ?: ""
        )
    } ?: BePsRoomFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestRoomList.StubCase.SUCCESS -> PsStubCase.ROOM_LIST_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.respondRoomCreate() = PsResponseRoomCreate(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondRoomRead() = PsResponseRoomRead(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondRoomUpdate() = PsResponseRoomUpdate(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondRoomDelete() = PsResponseRoomDelete(
    room = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondRoomList() = PsResponseRoomList(
    rooms = responseRooms.takeIf { it.isNotEmpty() }?.filter { it != BePsRoomModel.NONE }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

private fun PsRoomUpdateDto.toModel() = BePsRoomModel(
    id = id?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    length = length ?: Double.MIN_VALUE,
    width = width ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)

private fun PsRoomCreateDto.toModel() = BePsRoomModel(
    name = name ?: "",
    description = description ?: "",
    length = length ?: Double.MIN_VALUE,
    width = width ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)

private fun BePsRoomModel.toTransport() = PsRoomDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    length = length.takeIf { it != Double.MIN_VALUE },
    width = width.takeIf { it != Double.MIN_VALUE },
    actions = actions.takeIf { it.isNotEmpty() }
        ?.filter { it != BePsActionModel.NONE }?.map { it.toTransport() }?.toSet()
)

private fun PsRoomDto.toModel() = BePsRoomModel(
    id = id?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    length = length ?: Double.MIN_VALUE,
    width = width ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)
