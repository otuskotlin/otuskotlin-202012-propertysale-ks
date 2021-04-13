package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
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

class FlatService(private val crud: FlatCrud) {

    suspend fun list(context: BePsContext, query: PsRequestFlatList?): PsResponseFlatList = with(context) {
        query?.also { setQuery(it) }
        crud.list(this)
        return respondFlatList()
    }

    suspend fun create(context: BePsContext, query: PsRequestFlatCreate?): PsResponseFlatCreate = with(context) {
        query?.also { setQuery(it) }
        crud.create(this)
        return respondFlatCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestFlatRead?): PsResponseFlatRead = with(context) {
        query?.also { setQuery(it) }
        crud.read(this)
        return respondFlatRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestFlatUpdate?): PsResponseFlatUpdate = with(context) {
        query?.also { setQuery(it) }
        crud.update(this)
        return respondFlatUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestFlatDelete?): PsResponseFlatDelete = with(context) {
        query?.also { setQuery(it) }
        crud.delete(this)
        return respondFlatDelete()
    }
}
