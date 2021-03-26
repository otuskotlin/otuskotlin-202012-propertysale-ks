package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
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

private val crud = RoomCrud()

@Post("/room/list")
fun roomList(): String? = handle { query: PsRequestRoomList? ->
    query?.also { setQuery(it) }
    crud.list(this)
    respondRoomList()
}

@Post("/room/create")
fun roomCreate(): String? = handle { query: PsRequestRoomCreate? ->
    query?.also { setQuery(it) }
    crud.create(this)
    respondRoomCreate()
}

@Post("/room/read")
fun roomRead(): String? = handle { query: PsRequestRoomRead? ->
    query?.also { setQuery(it) }
    crud.read(this)
    respondRoomRead()
}

@Post("/room/update")
fun roomUpdate(): String? = handle { query: PsRequestRoomUpdate? ->
    query?.also { setQuery(it) }
    crud.update(this)
    respondRoomUpdate()
}

@Post("/room/delete")
fun roomDelete(): String? = handle { query: PsRequestRoomDelete? ->
    query?.also { setQuery(it) }
    crud.delete(this)
    respondRoomDelete()
}
