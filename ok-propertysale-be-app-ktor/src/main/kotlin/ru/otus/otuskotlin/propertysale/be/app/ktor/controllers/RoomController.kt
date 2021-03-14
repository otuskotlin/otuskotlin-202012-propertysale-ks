package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomModel
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
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

class RoomController {

    private val room = BePsRoomModel(
        id = BePsRoomIdModel("room-id"),
        name = "room-name",
        description = "room-description",
        length = 7.0,
        width = 5.0,
        actions = mutableSetOf(
            BePsActionModel(BePsActionIdModel("action-1")),
            BePsActionModel(BePsActionIdModel("action-2")),
            BePsActionModel(BePsActionIdModel("action-3"))
        )
    )

    suspend fun read(query: PsRequestRoomRead): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseRoom = room
            respondRoomRead().copy(
                responseId = "room-read-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseRoomRead(
                responseId = "room-read-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun create(query: PsRequestRoomCreate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseRoom = room
            respondRoomCreate().copy(
                responseId = "room-create-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseRoomCreate(
                responseId = "room-create-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun update(query: PsRequestRoomUpdate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseRoom = room
            respondRoomUpdate().copy(
                responseId = "room-update-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseRoomUpdate(
                responseId = "room-update-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun delete(query: PsRequestRoomDelete): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseRoom = room
            respondRoomDelete().copy(
                responseId = "room-delete-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseRoomDelete(
                responseId = "room-delete-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun list(query: PsRequestRoomList): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseRooms = mutableListOf(room)
            respondRoomList().copy(
                responseId = "room-list-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseRoomList(
                responseId = "room-list-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }
}
