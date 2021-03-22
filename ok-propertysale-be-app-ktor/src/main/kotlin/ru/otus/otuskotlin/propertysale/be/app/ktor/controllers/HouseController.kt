package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
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

fun Routing.houseRouting(crud: HouseCrud) {
    post(RestEndpoints.houseList) {
        handleRoute<PsRequestHouseList, PsResponseHouseList> { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondHouseList()
        }
    }
    post(RestEndpoints.houseCreate) {
        handleRoute<PsRequestHouseCreate, PsResponseHouseCreate> { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondHouseCreate()
        }
    }
    post(RestEndpoints.houseRead) {
        handleRoute<PsRequestHouseRead, PsResponseHouseRead> { query ->
            query?.also { setQuery(it) }
            crud.read(this)
            respondHouseRead()
        }
    }
    post(RestEndpoints.houseUpdate) {
        handleRoute<PsRequestHouseUpdate, PsResponseHouseUpdate> { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondHouseUpdate()
        }
    }
    post(RestEndpoints.houseDelete) {
        handleRoute<PsRequestHouseDelete, PsResponseHouseDelete> { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondHouseDelete()
        }
    }
}
