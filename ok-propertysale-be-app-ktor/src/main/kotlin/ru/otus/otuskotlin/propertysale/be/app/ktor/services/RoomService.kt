package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondRoomUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
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

class RoomService(private val crud: RoomCrud) {

    suspend fun list(context: BePsContext, query: PsRequestRoomList?): PsResponseRoomList = with(context) {
        query?.also { setQuery(it) }
        crud.list(this)
        return respondRoomList()
    }

    suspend fun create(context: BePsContext, query: PsRequestRoomCreate?): PsResponseRoomCreate = with(context) {
        query?.also { setQuery(it) }
        crud.create(this)
        return respondRoomCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestRoomRead?): PsResponseRoomRead = with(context) {
        query?.also { setQuery(it) }
        crud.read(this)
        return respondRoomRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestRoomUpdate?): PsResponseRoomUpdate = with(context) {
        query?.also { setQuery(it) }
        crud.update(this)
        return respondRoomUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestRoomDelete?): PsResponseRoomDelete = with(context) {
        query?.also { setQuery(it) }
        crud.delete(this)
        return respondRoomDelete()
    }
}
