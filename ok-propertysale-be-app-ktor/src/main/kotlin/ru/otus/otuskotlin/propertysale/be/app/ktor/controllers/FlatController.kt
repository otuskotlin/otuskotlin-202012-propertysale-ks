package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
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

fun Routing.flatRouting(crud: FlatCrud) {
    post(RestEndpoints.flatList) {
        handleRoute<PsRequestFlatList, PsResponseFlatList> { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondFlatList()
        }
    }
    post(RestEndpoints.flatCreate) {
        handleRoute<PsRequestFlatCreate, PsResponseFlatCreate> { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondFlatCreate()
        }
    }
    post(RestEndpoints.flatRead) {
        handleRoute<PsRequestFlatRead, PsResponseFlatRead> { query ->
            query?.also { setQuery(it) }
            crud.read(this)
            respondFlatRead()
        }
    }
    post(RestEndpoints.flatUpdate) {
        handleRoute<PsRequestFlatUpdate, PsResponseFlatUpdate> { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondFlatUpdate()
        }
    }
    post(RestEndpoints.flatDelete) {
        handleRoute<PsRequestFlatDelete, PsResponseFlatDelete> { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondFlatDelete()
        }
    }
}
