package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.PsRoomDto
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
import java.time.Instant

class RoomController {
    fun list(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestRoomList::class.java)
        val response = PsResponseRoomList(
            rooms = listOf(
                mockRead("room-id-001"),
                mockRead("room-id-002"),
                mockRead("room-id-003"),
                mockRead("room-id-004"),
                mockRead("room-id-005"),
                mockRead("room-id-006"),
                mockRead("room-id-007")
            )
        )
        return ok().body(response)
    }

    fun create(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestRoomCreate::class.java)
        val response = PsResponseRoomCreate(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            room = mockUpdate(
                id = "test-room-id",
                name = query.createData?.name,
                description = query.createData?.description,
                length = query.createData?.length,
                width = query.createData?.width
            )
        )
        return ok().body(response)
    }

    fun read(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestRoomRead::class.java)
        val response = PsResponseRoomRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            room = mockRead(query.roomId ?: "")
        )
        return ok().body(response)
    }

    fun update(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestRoomUpdate::class.java)
        val id = query.updateData?.id
        val response = if (id != null)
            PsResponseRoomUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                room = mockUpdate(
                    id = id,
                    name = query.updateData?.name,
                    description = query.updateData?.description,
                    length = query.updateData?.length,
                    width = query.updateData?.width
                )
            )
        else
            PsResponseRoomUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                errors = listOf(
                    ErrorDto(
                        code = "wrong-id",
                        group = "validation",
                        field = "id",
                        level = ErrorDto.Level.ERROR,
                        message = "id of the demand to be updated cannot be empty"
                    )
                )
            )
        return ok().body(response)
    }

    fun delete(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestRoomDelete::class.java)
        return ok().body(
            PsResponseRoomDelete(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                room = mockRead(query.roomId ?: ""),
                deleted = true
            )
        )
    }

    companion object {
        fun mockUpdate(
            id: String,
            name: String?,
            description: String?,
            length: Double?,
            width: Double?,
        ) = PsRoomDto(
            id = id,
            name = name,
            description = description,
            length = length,
            width = width,
            actions = setOf(PsActionDto("action-1"), PsActionDto("action-2"), PsActionDto("action-3")),
        )

        fun mockRead(id: String) = mockUpdate(
            id = id,
            name = "Room $id",
            description = "Description of room $id",
            length = 7.0,
            width = 5.0
        )
    }
}
