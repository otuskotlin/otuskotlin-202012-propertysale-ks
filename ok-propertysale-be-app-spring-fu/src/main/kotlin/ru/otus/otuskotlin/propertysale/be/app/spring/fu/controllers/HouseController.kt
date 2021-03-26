package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
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

class HouseController(private val crud: HouseCrud) {
    fun list(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestHouseList? ->
        query?.also { setQuery(it) }
        crud.list(this)
        respondHouseList()
    }

    fun create(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestHouseCreate? ->
        query?.also { setQuery(it) }
        crud.create(this)
        respondHouseCreate()
    }

    fun read(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestHouseRead? ->
        query?.also { setQuery(it) }
        crud.read(this)
        respondHouseRead()
    }

    fun update(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestHouseUpdate? ->
        query?.also { setQuery(it) }
        crud.update(this)
        respondHouseUpdate()
    }

    fun delete(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestHouseDelete? ->
        query?.also { setQuery(it) }
        crud.delete(this)
        respondHouseDelete()
    }
}
