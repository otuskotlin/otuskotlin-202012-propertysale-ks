package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
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

@Post("/room/list")
fun roomList(): String? = handle { _: PsRequestRoomList ->
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
}

@Post("/room/create")
fun roomCreate(): String? = handle { query: PsRequestRoomCreate ->
    PsResponseRoomCreate(
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
}

@Post("/room/read")
fun roomRead(): String? = handle { query: PsRequestRoomRead ->
    PsResponseRoomRead(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        room = mockRead(query.roomId ?: "")
    )
}

@Post("/room/update")
fun roomUpdate(): String? = handle { query: PsRequestRoomUpdate ->
    PsResponseRoomUpdate(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        room = mockUpdate(
            id = query.updateData?.id ?: "",
            name = query.updateData?.name,
            description = query.updateData?.description,
            length = query.updateData?.length,
            width = query.updateData?.width
        )
    )
}

@Post("/room/delete")
fun roomDelete(): String? = handle { query: PsRequestRoomDelete ->
    PsResponseRoomDelete(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        room = mockRead(query.roomId ?: ""),
        deleted = true
    )
}

private fun mockUpdate(
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

private fun mockRead(id: String) = mockUpdate(
    id = id,
    name = "Room $id",
    description = "Description of room $id",
    length = 7.0,
    width = 5.0
)
