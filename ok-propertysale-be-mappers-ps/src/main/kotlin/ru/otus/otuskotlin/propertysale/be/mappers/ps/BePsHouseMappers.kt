package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.models.PsHouseCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.models.PsHouseUpdateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseUpdate
import java.time.Instant

fun BePsContext.setQuery(query: PsRequestHouseRead) = setQuery(query) {
    requestHouseId = query.houseId?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestHouseRead.StubCase.SUCCESS -> PsStubCase.HOUSE_READ_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestHouseCreate) = setQuery(query) {
    requestHouse = query.createData?.toModel() ?: BePsHouseModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestHouseCreate.StubCase.SUCCESS -> PsStubCase.HOUSE_CREATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestHouseUpdate) = setQuery(query) {
    requestHouse = query.updateData?.toModel() ?: BePsHouseModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestHouseUpdate.StubCase.SUCCESS -> PsStubCase.HOUSE_UPDATE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestHouseDelete) = setQuery(query) {
    requestHouseId = query.houseId?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestHouseDelete.StubCase.SUCCESS -> PsStubCase.HOUSE_DELETE_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.setQuery(query: PsRequestHouseList) = setQuery(query) {
    houseFilter = query.filterData?.let {
        BePsHouseFilterModel(
            text = it.text ?: ""
        )
    } ?: BePsHouseFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        PsRequestHouseList.StubCase.SUCCESS -> PsStubCase.HOUSE_LIST_SUCCESS
        else -> PsStubCase.NONE
    }
}

fun BePsContext.respondHouseCreate() = PsResponseHouseCreate(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondHouseRead() = PsResponseHouseRead(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondHouseUpdate() = PsResponseHouseUpdate(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondHouseDelete() = PsResponseHouseDelete(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun BePsContext.respondHouseList() = PsResponseHouseList(
    houses = responseHouses.takeIf { it.isNotEmpty() }?.filter { it != BePsHouseModel.NONE }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

private fun PsHouseUpdateDto.toModel() = BePsHouseModel(
    id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    area = area ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)

private fun PsHouseCreateDto.toModel() = BePsHouseModel(
    name = name ?: "",
    description = description ?: "",
    area = area ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)

internal fun BePsHouseModel.toTransport() = PsHouseDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    area = area.takeIf { it != Double.MIN_VALUE },
    actions = actions.takeIf { it.isNotEmpty() }
        ?.filter { it != BePsActionModel.NONE }?.map { it.toTransport() }?.toSet()
)

private fun PsHouseDto.toModel() = BePsHouseModel(
    id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    area = area ?: Double.MIN_VALUE,
    actions = actions?.map { it.toModel() }?.toMutableSet() ?: mutableSetOf()
)
