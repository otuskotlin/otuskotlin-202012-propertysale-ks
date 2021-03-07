package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate

fun BePsContext.setQuery(request: PsRequestHouseRead) {
    this.requestHouseId = request.id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
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
    this.requestHouseId = request.id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE
}

fun PsHouseDto.toInternal() = BePsHouseModel(
    id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
    name = name ?: "",
    description = description ?: "",
    area = area ?: Double.MIN_VALUE,
    actions = actions?.map { it.toInternal() }?.toMutableSet() ?: mutableSetOf()
)
