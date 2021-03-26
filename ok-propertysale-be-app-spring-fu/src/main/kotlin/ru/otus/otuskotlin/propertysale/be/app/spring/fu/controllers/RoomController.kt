package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate

class RoomController(private val crud: RoomCrud) {
    fun list(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestRoomList? ->
        query?.also { setQuery(it) }
        crud.list(this)
        respondRoomList()
    }

    fun create(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestRoomCreate? ->
        query?.also { setQuery(it) }
        crud.create(this)
        respondRoomCreate()
    }

    fun read(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestRoomRead? ->
        query?.also { setQuery(it) }
        crud.read(this)
        respondRoomRead()
    }

    fun update(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestRoomUpdate? ->
        query?.also { setQuery(it) }
        crud.update(this)
        respondRoomUpdate()
    }

    fun delete(request: ServerRequest): ServerResponse = handleRoute(request) { query: PsRequestRoomDelete? ->
        query?.also { setQuery(it) }
        crud.delete(this)
        respondRoomDelete()
    }
}
