package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
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

class FlatController {

    private val flat = BePsFlatModel(
        id = BePsFlatIdModel("flat-id"),
        name = "flat-name",
        description = "flat-description",
        floor = 5,
        numberOfRooms = 2,
        actions = mutableSetOf(
            BePsActionModel(BePsActionIdModel("action-1")),
            BePsActionModel(BePsActionIdModel("action-2"))
        )
    )

    suspend fun read(query: PsRequestFlatRead): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseFlat = flat
            respondFlatRead().copy(
                responseId = "flat-read-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseFlatRead(
                responseId = "flat-read-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun create(query: PsRequestFlatCreate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseFlat = flat
            respondFlatCreate().copy(
                responseId = "flat-create-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseFlatCreate(
                responseId = "flat-create-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun update(query: PsRequestFlatUpdate): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseFlat = flat
            respondFlatUpdate().copy(
                responseId = "flat-update-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseFlatUpdate(
                responseId = "flat-update-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun delete(query: PsRequestFlatDelete): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseFlat = flat
            respondFlatDelete().copy(
                responseId = "flat-delete-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseFlatDelete(
                responseId = "flat-delete-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }

    suspend fun list(query: PsRequestFlatList): PsMessage = BePsContext().run {
        try {
            setQuery(query)
            responseFlats = mutableListOf(flat)
            respondFlatList().copy(
                responseId = "flat-list-response-id",
                status = ResponseStatusDto.SUCCESS,
                onRequest = query.requestId
            )
        } catch (e: Throwable) {
            PsResponseFlatList(
                responseId = "flat-list-response-id",
                onRequest = query.requestId,
                status = ResponseStatusDto.INTERNAL_SERVER_ERROR,
            )
        }
    }
}
