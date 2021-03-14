package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
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

@Post("/house/list")
fun houseList(): String? = handle { _: PsRequestHouseList ->
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
}

@Post("/house/create")
fun houseCreate(): String? = handle { query: PsRequestHouseCreate ->
    PsResponseHouseCreate(
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
}

@Post("/house/read")
fun houseRead(): String? = handle { query: PsRequestHouseRead ->
    PsResponseHouseRead(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        house = mockRead(query.houseId ?: "")
    )
}

@Post("/house/update")
fun houseUpdate(): String? = handle { query: PsRequestHouseUpdate ->
    PsResponseHouseUpdate(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        house = mockUpdate(
            id = query.updateData?.id ?: "",
            name = query.updateData?.name,
            description = query.updateData?.description,
            area = query.updateData?.area
        )
    )
}

@Post("/house/delete")
fun houseDelete(): String? = handle { query: PsRequestHouseDelete ->
    PsResponseHouseDelete(
        responseId = "msg-id-123",
        onRequest = query.requestId,
        endTime = Instant.now().toString(),
        status = ResponseStatusDto.SUCCESS,
        house = mockRead(query.houseId ?: ""),
        deleted = true
    )
}

private fun mockUpdate(
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

private fun mockRead(id: String) = mockUpdate(
    id = id,
    name = "House $id",
    description = "Description of house $id",
    area = 150.0
)
