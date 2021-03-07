package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ErrorDto
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

class FlatController {
    fun list(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestFlatList::class.java)
        val response = PsResponseFlatList(
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
        return ok().body(response)
    }

    fun create(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestFlatCreate::class.java)
        val response = PsResponseFlatCreate(
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
        return ok().body(response)
    }

    fun read(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestFlatRead::class.java)
        val response = PsResponseFlatRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            flat = mockRead(query.flatId ?: "")
        )
        return ok().body(response)
    }

    fun update(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestFlatUpdate::class.java)
        val id = query.updateData?.id
        val response = if (id != null)
            PsResponseFlatUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                flat = mockUpdate(
                    id = id,
                    name = query.updateData?.name,
                    description = query.updateData?.description,
                    floor = query.updateData?.floor,
                    numberOfRooms = query.updateData?.numberOfRooms
                )
            )
        else
            PsResponseFlatUpdate(
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
        val query = request.body(PsRequestFlatDelete::class.java)
        return ok().body(
            PsResponseFlatDelete(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                flat = mockRead(query.flatId ?: ""),
                deleted = true
            )
        )
    }

    companion object {
        fun mockUpdate(
            id: String,
            name: String?,
            description: String?,
            floor: Int?,
            numberOfRooms: Int?,
        ) = PsFlatDto(
            id = id,
            name = name,
            description = description,
            floor = floor,
            numberOfRooms = numberOfRooms,
            actions = setOf(PsActionDto("action-1"), PsActionDto("action-2")),
        )

        fun mockRead(id: String) = mockUpdate(
            id = id,
            name = "Flat $id",
            description = "Description of flat $id",
            floor = 5,
            numberOfRooms = 2
        )
    }
}
