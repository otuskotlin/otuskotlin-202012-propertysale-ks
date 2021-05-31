package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.handleRoute
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

fun Routing.roomRouting(service: RoomService, authOff: Boolean = false) {
    post(RestEndpoints.roomList) {
        handleRoute<PsRequestRoomList, PsResponseRoomList> { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.roomCreate) {
        handleRoute<PsRequestRoomCreate, PsResponseRoomCreate> { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.roomRead) {
        handleRoute<PsRequestRoomRead, PsResponseRoomRead> { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.roomUpdate) {
        handleRoute<PsRequestRoomUpdate, PsResponseRoomUpdate> { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.roomDelete) {
        handleRoute<PsRequestRoomDelete, PsResponseRoomDelete> { query ->
            service.delete(this, query)
        }
    }
}
