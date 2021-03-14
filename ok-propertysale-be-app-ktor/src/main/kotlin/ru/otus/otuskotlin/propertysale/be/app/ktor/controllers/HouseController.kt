package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
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

class HouseController {

    private val house = BePsHouseModel(
        id = BePsHouseIdModel("house-id"),
        name = "house-name",
        description = "house-description",
        area = 150.0,
        actions = mutableSetOf(
            BePsActionModel(BePsActionIdModel("action-1"))
        )
    )

    suspend fun read(query: PsRequestHouseRead): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseHouse = house
            respondHouseRead().copy(
                responseId = "house-read-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseHouseRead(
                responseId = "house-read-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun create(query: PsRequestHouseCreate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseHouse = house
            respondHouseCreate().copy(
                responseId = "house-create-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseHouseCreate(
                responseId = "house-create-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun update(query: PsRequestHouseUpdate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseHouse = house
            respondHouseUpdate().copy(
                responseId = "house-update-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseHouseUpdate(
                responseId = "house-update-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun delete(query: PsRequestHouseDelete): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseHouse = house
            respondHouseDelete().copy(
                responseId = "house-delete-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseHouseDelete(
                responseId = "house-delete-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun list(query: PsRequestHouseList): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseHouses = mutableListOf(house)
            respondHouseList().copy(
                responseId = "house-list-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseHouseList(
                responseId = "house-list-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }
}
