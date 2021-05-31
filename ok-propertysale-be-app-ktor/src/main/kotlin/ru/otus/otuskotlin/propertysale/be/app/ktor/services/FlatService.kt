package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import org.slf4j.event.Level
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.be.mappers.ps.toLog
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatUpdate

class FlatService(private val crud: FlatCrud) {

    private val logger = psLogger(this::class.java)

    suspend fun list(context: BePsContext, query: PsRequestFlatList?): PsResponseFlatList = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("flat-list-request-got")
        )
        crud.list(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("flat-list-request-handled")
        )
        return respondFlatList()
    }

    suspend fun create(context: BePsContext, query: PsRequestFlatCreate?): PsResponseFlatCreate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("flat-create-request-got")
        )
        crud.create(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("flat-create-request-handled")
        )
        return respondFlatCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestFlatRead?): PsResponseFlatRead = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("flat-read-request-got")
        )
        crud.read(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("flat-read-request-handled")
        )
        return respondFlatRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestFlatUpdate?): PsResponseFlatUpdate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("flat-update-request-got")
        )
        crud.update(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("flat-update-request-handled")
        )
        return respondFlatUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestFlatDelete?): PsResponseFlatDelete = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("flat-delete-request-got")
        )
        crud.delete(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("flat-delete-request-handled")
        )
        return respondFlatDelete()
    }
}
