package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseUpdate
import java.time.Instant

@RestController
@RequestMapping("/house")
class HouseController {

    @PostMapping("/list")
    fun list() =
        PsResponseHouseList(
            houses = listOf(
                mockRead("house-id-001"),
                mockRead("house-id-002"),
                mockRead("house-id-003"),
                mockRead("house-id-004"),
                mockRead("house-id-005"),
                mockRead("house-id-006"),
                mockRead("house-id-007")
            )
        )

    @PostMapping("/create")
    fun create(@RequestBody query: PsRequestHouseCreate) =
        PsResponseHouseCreate(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            house = mockUpdate(
                id = "house-id-created",
                name = query.createData?.name,
                description = query.createData?.description,
                area = query.createData?.area
            )
        )

    @PostMapping("/read")
    fun read(@RequestBody query: PsRequestHouseRead) =
        PsResponseHouseRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            house = mockRead(query.houseId ?: "")
        )

    @PostMapping("/update")
    fun update(@RequestBody query: PsRequestHouseUpdate) =
        if (query.updateData?.id != null)
            PsResponseHouseUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                house = mockUpdate(
                    id = query.updateData?.id!!,
                    name = query.updateData?.name,
                    description = query.updateData?.description,
                    area = query.updateData?.area
                )
            )
        else
            PsResponseHouseUpdate(
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
                        message = "id of the house to be updated cannot be empty"
                    )
                )
            )

    @PostMapping("/delete")
    fun delete(@RequestBody query: PsRequestHouseDelete) =
        PsResponseHouseDelete(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            house = mockRead(query.houseId ?: ""),
            deleted = true
        )

    companion object {
        fun mockUpdate(
            id: String,
            name: String?,
            description: String?,
            area: Double?,
        ) = PsHouseDto(
            id = id,
            name = name,
            description = description,
            area = area,
            actions = setOf(PsActionDto("action-1"))
        )

        fun mockRead(id: String) = mockUpdate(
            id = id,
            name = "House $id",
            description = "Description of house $id",
            area = 150.0
        )
    }
}
