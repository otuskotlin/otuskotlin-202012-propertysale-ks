package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import org.slf4j.event.Level
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.be.mappers.ps.toLog
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomUpdate

class RoomService(private val crud: RoomCrud) {

    private val logger = psLogger(this::class.java)

    suspend fun list(context: BePsContext, query: PsRequestRoomList?): PsResponseRoomList = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("room-list-request-got")
        )
        crud.list(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("room-list-request-handled")
        )
        return respondRoomList()
    }

    suspend fun create(context: BePsContext, query: PsRequestRoomCreate?): PsResponseRoomCreate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("room-create-request-got")
        )
        crud.create(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("room-create-request-handled")
        )
        return respondRoomCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestRoomRead?): PsResponseRoomRead = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("room-read-request-got")
        )
        crud.read(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("room-read-request-handled")
        )
        return respondRoomRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestRoomUpdate?): PsResponseRoomUpdate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("room-update-request-got")
        )
        crud.update(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("room-update-request-handled")
        )
        return respondRoomUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestRoomDelete?): PsResponseRoomDelete = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("room-delete-request-got")
        )
        crud.delete(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("room-delete-request-handled")
        )
        return respondRoomDelete()
    }
}
