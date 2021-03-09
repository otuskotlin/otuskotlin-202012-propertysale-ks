package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatUpdate
import java.time.Instant

@Post("/flat/list")
fun flatList(): String? = handle { _: PsRequestFlatList ->
    PsResponseFlatList(
        flats = listOf(
            mockRead("flat-id-001"),
            mockRead("flat-id-002"),
            mockRead("flat-id-003"),
            mockRead("flat-id-004"),
            mockRead("flat-id-005"),
            mockRead("flat-id-006"),
            mockRead("flat-id-007")
        )
    )
}

@Post("/flat/create")
fun flatCreate(): String? = handle { query: PsRequestFlatCreate ->
    PsResponseFlatCreate(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        flat = mockUpdate(
            id = "test-flat-id",
            name = query.createData?.name,
            description = query.createData?.description,
            floor = query.createData?.floor,
            numberOfRooms = query.createData?.numberOfRooms
        )
    )
}

@Post("/flat/read")
fun flatRead(): String? = handle { query: PsRequestFlatRead ->
    PsResponseFlatRead(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        flat = mockRead(query.flatId ?: "")
    )
}

@Post("/flat/update")
fun flatUpdate(): String? = handle { query: PsRequestFlatUpdate ->
    PsResponseFlatUpdate(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        flat = mockUpdate(
            id = query.updateData?.id ?: "",
            name = query.updateData?.name,
            description = query.updateData?.description,
            floor = query.updateData?.floor,
            numberOfRooms = query.updateData?.numberOfRooms
        )
    )
}

@Post("/flat/delete")
fun flatDelete(): String? = handle { query: PsRequestFlatDelete ->
    PsResponseFlatDelete(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        flat = mockRead(query.flatId ?: ""),
        deleted = true
    )
}

private fun mockUpdate(
    id: String,
    name: String?,
    description: String?,
    floor: Int?,
    numberOfRooms: Int?
) = PsFlatDto(
    id = id,
    name = name,
    description = description,
    floor = floor,
    numberOfRooms = numberOfRooms,
    actions = setOf(PsActionDto("action-1"), PsActionDto("action-2"))
)

private fun mockRead(id: String) = mockUpdate(
    id = id,
    name = "Flat $id",
    description = "Description of flat $id",
    floor = 5,
    numberOfRooms = 2
)
