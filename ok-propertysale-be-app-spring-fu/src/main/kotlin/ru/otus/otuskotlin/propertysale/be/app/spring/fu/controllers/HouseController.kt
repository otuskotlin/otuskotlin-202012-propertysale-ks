package ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate
import java.time.Instant

class HouseController {
    fun list(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestHouseList::class.java)
        val response = PsResponseHouseList(
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
        return ok().body(response)
    }

    fun create(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestHouseCreate::class.java)
        val response = PsResponseHouseCreate(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            house = mockUpdate(
                id = "test-house-id",
                name = query.createData?.name,
                description = query.createData?.description,
                area = query.createData?.area
            )
        )
        return ok().body(response)
    }

    fun read(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestHouseRead::class.java)
        val response = PsResponseHouseRead(
            responseId = "msg-id-123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            house = mockRead(query.houseId ?: "")
        )
        return ok().body(response)
    }

    fun update(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestHouseUpdate::class.java)
        val id = query.updateData?.id
        val response = if (id != null)
            PsResponseHouseUpdate(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                house = mockUpdate(
                    id = id,
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
                        message = "id of the demand to be updated cannot be empty"
                    )
                )
            )
        return ok().body(response)
    }

    fun delete(request: ServerRequest): ServerResponse {
        val query = request.body(PsRequestHouseDelete::class.java)
        return ok().body(
            PsResponseHouseDelete(
                responseId = "msg-id-123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                house = mockRead(query.houseId ?: ""),
                deleted = true
            )
        )
    }

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
