package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
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

fun Routing.houseRouting(service: HouseService) {
    post(RestEndpoints.houseList) {
        handleRoute<PsRequestHouseList, PsResponseHouseList> { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.houseCreate) {
        handleRoute<PsRequestHouseCreate, PsResponseHouseCreate> { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.houseRead) {
        handleRoute<PsRequestHouseRead, PsResponseHouseRead> { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.houseUpdate) {
        handleRoute<PsRequestHouseUpdate, PsResponseHouseUpdate> { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.houseDelete) {
        handleRoute<PsRequestHouseDelete, PsResponseHouseDelete> { query ->
            service.delete(this, query)
        }
    }
}
