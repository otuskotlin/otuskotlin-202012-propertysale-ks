package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomUpdate

private val logger = psLogger(Routing::roomRouting::class.java)

fun Routing.roomRouting(service: RoomService, authOff: Boolean = false) {

    post(RestEndpoints.roomList) {
        handleRoute<PsRequestRoomList, PsResponseRoomList>("room-list", logger) { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.roomCreate) {
        handleRoute<PsRequestRoomCreate, PsResponseRoomCreate>("room-create", logger) { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.roomRead) {
        handleRoute<PsRequestRoomRead, PsResponseRoomRead>("room-read", logger) { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.roomUpdate) {
        handleRoute<PsRequestRoomUpdate, PsResponseRoomUpdate>("room-update", logger) { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.roomDelete) {
        handleRoute<PsRequestRoomDelete, PsResponseRoomDelete>("room-delete", logger) { query ->
            service.delete(this, query)
        }
    }
}
