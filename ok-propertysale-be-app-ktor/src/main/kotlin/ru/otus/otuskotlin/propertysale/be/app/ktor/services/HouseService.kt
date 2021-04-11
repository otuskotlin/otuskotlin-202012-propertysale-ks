package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate

class HouseService(private val crud: HouseCrud) {

    suspend fun list(context: BePsContext, query: PsRequestHouseList?): PsResponseHouseList = with(context) {
        query?.also { setQuery(it) }
        crud.list(this)
        return respondHouseList()
    }

    suspend fun create(context: BePsContext, query: PsRequestHouseCreate?): PsResponseHouseCreate = with(context) {
        query?.also { setQuery(it) }
        crud.create(this)
        return respondHouseCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestHouseRead?): PsResponseHouseRead = with(context) {
        query?.also { setQuery(it) }
        crud.read(this)
        return respondHouseRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestHouseUpdate?): PsResponseHouseUpdate = with(context) {
        query?.also { setQuery(it) }
        crud.update(this)
        return respondHouseUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestHouseDelete?): PsResponseHouseDelete = with(context) {
        query?.also { setQuery(it) }
        crud.delete(this)
        return respondHouseDelete()
    }
}
