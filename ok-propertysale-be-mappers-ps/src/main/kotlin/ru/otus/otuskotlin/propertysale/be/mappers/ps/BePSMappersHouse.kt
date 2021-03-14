package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate

fun BePsContext.setQuery(request: PsRequestHouseRead) {
    this.requestHouseId = request.houseId?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
}

fun BePsContext.setQuery(request: PsRequestHouseCreate) {
    request.createData?.let { data ->
        this.requestHouse = BePsHouseModel(
            name = data.name ?: "",
            description = data.description ?: "",
            area = data.area ?: Double.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestHouseUpdate) {
    request.updateData?.let { data ->
        this.requestHouse = BePsHouseModel(
            id = data.id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
            name = data.name ?: "",
            description = data.description ?: "",
            area = data.area ?: Double.MIN_VALUE,
            actions = data.actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}

fun BePsContext.setQuery(request: PsRequestHouseDelete) {
    this.requestHouseId = request.houseId?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
}

fun BePsContext.respondHouseRead() = PsResponseHouseRead(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport()
)

fun BePsContext.respondHouseCreate() = PsResponseHouseCreate(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport()
)

fun BePsContext.respondHouseUpdate() = PsResponseHouseUpdate(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport()
)

fun BePsContext.respondHouseDelete() = PsResponseHouseDelete(
    house = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport()
)

fun BePsContext.respondHouseList() = PsResponseHouseList(
    houses = responseHouses.takeIf { it.isNotEmpty() }?.filter { it != BePsHouseModel.NONE }
        ?.map { it.toTransport() }
)

internal fun BePsHouseModel.toTransport() = PsHouseDto(
    id = id.id.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    area = area.takeIf { it != Double.MIN_VALUE },
    actions = actions.takeIf { it.isNotEmpty() }
        ?.filter { it != BePsActionModel.NONE }?.map { it.toTransport() }?.toSet()
)

fun PsHouseDto.toInternal() = BePsHouseModel(
    id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    area = area ?: Double.MIN_VALUE,
    actions = actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)
