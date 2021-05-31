package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
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

private val logger = psLogger(Routing::houseRouting::class.java)

fun Routing.houseRouting(service: HouseService, authOff: Boolean = false) {

    post(RestEndpoints.houseList) {
        handleRoute<PsRequestHouseList, PsResponseHouseList>("house-list", logger) { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.houseCreate) {
        handleRoute<PsRequestHouseCreate, PsResponseHouseCreate>("house-create", logger) { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.houseRead) {
        handleRoute<PsRequestHouseRead, PsResponseHouseRead>("house-read", logger) { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.houseUpdate) {
        handleRoute<PsRequestHouseUpdate, PsResponseHouseUpdate>("house-update", logger) { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.houseDelete) {
        handleRoute<PsRequestHouseDelete, PsResponseHouseDelete>("house-delete", logger) { query ->
            service.delete(this, query)
        }
    }
}
