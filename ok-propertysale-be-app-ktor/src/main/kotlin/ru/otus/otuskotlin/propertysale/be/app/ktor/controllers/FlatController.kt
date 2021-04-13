package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatUpdate

fun Routing.flatRouting(service: FlatService) {
    post(RestEndpoints.flatList) {
        handleRoute<PsRequestFlatList, PsResponseFlatList> { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.flatCreate) {
        handleRoute<PsRequestFlatCreate, PsResponseFlatCreate> { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.flatRead) {
        handleRoute<PsRequestFlatRead, PsResponseFlatRead> { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.flatUpdate) {
        handleRoute<PsRequestFlatUpdate, PsResponseFlatUpdate> { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.flatDelete) {
        handleRoute<PsRequestFlatDelete, PsResponseFlatDelete> { query ->
            service.delete(this, query)
        }
    }
}
