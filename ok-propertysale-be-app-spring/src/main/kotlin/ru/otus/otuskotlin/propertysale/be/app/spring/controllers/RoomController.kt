package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.PsRoomDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomUpdate
import java.time.Instant

@RestController
@RequestMapping("/room")
class RoomController {

    @PostMapping("/list")
    fun list() =
        PsResponseRoomList(
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

    @PostMapping("/create")
    fun create(@RequestBody query: PsRequestRoomCreate) =
        PsResponseRoomCreate(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            room = mockUpdate(
                id = "room-id-created",
                name = query.createData?.name,
                description = query.createData?.description,
                length = query.createData?.length,
                width = query.createData?.width
            )
        )

    @PostMapping("/read")
    fun read(@RequestBody query: PsRequestRoomRead) =
        PsResponseRoomRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            room = mockRead(query.roomId ?: "")
        )

    @PostMapping("/update")
    fun update(@RequestBody query: PsRequestRoomUpdate) =
        if (query.updateData?.id != null)
            PsResponseRoomUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                room = mockUpdate(
                    id = query.updateData?.id!!,
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

    @PostMapping("/delete")
    fun delete(@RequestBody query: PsRequestRoomDelete) =
        PsResponseRoomDelete(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            room = mockRead(query.roomId ?: ""),
            deleted = true
        )

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
