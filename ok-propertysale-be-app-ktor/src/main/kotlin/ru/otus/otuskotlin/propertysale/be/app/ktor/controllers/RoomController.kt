package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomUpdate

fun Routing.roomRouting(crud: RoomCrud) {
    post(RestEndpoints.roomList) {
        handleRoute<PsRequestRoomList, PsResponseRoomList> { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondRoomList()
        }
    }
    post(RestEndpoints.roomCreate) {
        handleRoute<PsRequestRoomCreate, PsResponseRoomCreate> { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondRoomCreate()
        }
    }
    post(RestEndpoints.roomRead) {
        handleRoute<PsRequestRoomRead, PsResponseRoomRead> { query ->
            query?.also { setQuery(it) }
            crud.read(this)
            respondRoomRead()
        }
    }
    post(RestEndpoints.roomUpdate) {
        handleRoute<PsRequestRoomUpdate, PsResponseRoomUpdate> { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondRoomUpdate()
        }
    }
    post(RestEndpoints.roomDelete) {
        handleRoute<PsRequestRoomDelete, PsResponseRoomDelete> { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondRoomDelete()
        }
    }
}
