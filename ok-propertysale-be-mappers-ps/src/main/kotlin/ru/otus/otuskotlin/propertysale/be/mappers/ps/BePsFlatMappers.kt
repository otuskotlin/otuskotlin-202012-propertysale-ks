package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.models.PsFlatCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.models.PsFlatUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatUpdate
import java.time.Instant

fun BePsContext.setQuery(query: PsRequestFlatRead) = setQuery(query) {
    requestFlatId = query.flatId?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestFlatRead.StubCase.SUCCESS -> PsStubCase.FLAT_READ_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestFlatCreate) = setQuery(query) {
    requestFlat = query.createData?.toModel() ?: BePsFlatModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestFlatCreate.StubCase.SUCCESS -> PsStubCase.FLAT_CREATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestFlatUpdate) = setQuery(query) {
    requestFlat = query.updateData?.toModel() ?: BePsFlatModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestFlatUpdate.StubCase.SUCCESS -> PsStubCase.FLAT_UPDATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestFlatDelete) = setQuery(query) {
    requestFlatId = query.flatId?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestFlatDelete.StubCase.SUCCESS -> PsStubCase.FLAT_DELETE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestFlatList) = setQuery(query) {
    flatFilter = query.filterData?.let {
        BePsFlatFilterModel(
            text = it.text ?: ""
        )
    } ?: BePsFlatFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestFlatList.StubCase.SUCCESS -> PsStubCase.FLAT_LIST_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.respondFlatCreate() = PsResponseFlatCreate(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondFlatRead() = PsResponseFlatRead(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondFlatUpdate() = PsResponseFlatUpdate(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondFlatDelete() = PsResponseFlatDelete(
    flat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondFlatList() = PsResponseFlatList(
    flats = responseFlats.takeIf { it.isNotEmpty() }?.filter { it != BePsFlatModel.NONE }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

private fun PsFlatUpdateDto.toModel() = BePsFlatModel(
    id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    floor = floor ?: Int.MIN_VALUE,
    numberOfRooms = numberOfRooms ?: Int.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)

private fun PsFlatCreateDto.toModel() = BePsFlatModel(
    name = name ?: "",
    description = description ?: "",
    floor = floor ?: Int.MIN_VALUE,
    numberOfRooms = numberOfRooms ?: Int.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
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

private fun PsFlatDto.toModel() = BePsFlatModel(
    id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    floor = floor ?: Int.MIN_VALUE,
    numberOfRooms = numberOfRooms ?: Int.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)
