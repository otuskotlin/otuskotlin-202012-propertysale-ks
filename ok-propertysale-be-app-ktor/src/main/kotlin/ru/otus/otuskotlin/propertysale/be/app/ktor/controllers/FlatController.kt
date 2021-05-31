package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
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

private val logger = psLogger(Routing::flatRouting::class.java)

fun Routing.flatRouting(service: FlatService, authOff: Boolean = false) {

    post(RestEndpoints.flatList) {
        handleRoute<PsRequestFlatList, PsResponseFlatList>("flat-list", logger) { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.flatCreate) {
        handleRoute<PsRequestFlatCreate, PsResponseFlatCreate>("flat-create", logger) { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.flatRead) {
        handleRoute<PsRequestFlatRead, PsResponseFlatRead>("flat-read", logger) { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.flatUpdate) {
        handleRoute<PsRequestFlatUpdate, PsResponseFlatUpdate>("flat-update", logger) { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.flatDelete) {
        handleRoute<PsRequestFlatDelete, PsResponseFlatDelete>("flat-delete", logger) { query ->
            service.delete(this, query)
        }
    }
}
