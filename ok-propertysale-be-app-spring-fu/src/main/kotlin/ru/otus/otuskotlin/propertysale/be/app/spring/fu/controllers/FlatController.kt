package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate

class FlatController(private val crud: FlatCrud) {
    fun list(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestFlatList? ->
        query?.also { setQuery(it) }
        crud.list(this)
        respondFlatList()
    }

    fun create(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestFlatCreate? ->
        query?.also { setQuery(it) }
        crud.create(this)
        respondFlatCreate()
    }

    fun read(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestFlatRead? ->
        query?.also { setQuery(it) }
        crud.read(this)
        respondFlatRead()
    }

    fun update(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestFlatUpdate? ->
        query?.also { setQuery(it) }
        crud.update(this)
        respondFlatUpdate()
    }

    fun delete(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestFlatDelete? ->
        query?.also { setQuery(it) }
        crud.delete(this)
        respondFlatDelete()
    }
}
