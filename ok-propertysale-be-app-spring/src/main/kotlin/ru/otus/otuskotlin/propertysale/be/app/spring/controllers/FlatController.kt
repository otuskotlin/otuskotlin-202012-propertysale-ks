package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatUpdate
import java.time.Instant

@RestController
@RequestMapping("/flat")
class FlatController {

    @PostMapping("/list")
    fun list() =
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

    @PostMapping("/create")
    fun create(@RequestBody query: PsRequestFlatCreate) =
        PsResponseFlatCreate(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            flat = mockUpdate(
                id = "flat-id-created",
                name = query.createData?.name,
                description = query.createData?.description,
                floor = query.createData?.floor,
                numberOfRooms = query.createData?.numberOfRooms
            )
        )

    @PostMapping("/read")
    fun read(@RequestBody query: PsRequestFlatRead) =
        PsResponseFlatRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            flat = mockRead(query.flatId ?: "")
        )

    @PostMapping("/update")
    fun update(@RequestBody query: PsRequestFlatUpdate) =
        if (query.updateData?.id != null)
            PsResponseFlatUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                flat = mockUpdate(
                    id = query.updateData?.id!!,
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
                        message = "id of the flat to be updated cannot be empty"
                    )
                )
            )

    @PostMapping("/delete")
    fun delete(@RequestBody query: PsRequestFlatDelete) =
        PsResponseFlatDelete(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            flat = mockRead(query.flatId ?: ""),
            deleted = true
        )

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
            actions = setOf(PsActionDto("action-1"), PsActionDto("action-2"))
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
