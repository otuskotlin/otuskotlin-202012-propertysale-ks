package ru.otus.otuskotlin.propertysale.be.app.ktor.services

import org.slf4j.event.Level
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.logging.psLogger
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.be.mappers.ps.toLog
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseUpdate

class HouseService(private val crud: HouseCrud) {

    private val logger = psLogger(this::class.java)

    suspend fun list(context: BePsContext, query: PsRequestHouseList?): PsResponseHouseList = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("house-list-request-got")
        )
        crud.list(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("house-list-request-handled")
        )
        return respondHouseList()
    }

    suspend fun create(context: BePsContext, query: PsRequestHouseCreate?): PsResponseHouseCreate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("house-create-request-got")
        )
        crud.create(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("house-create-request-handled")
        )
        return respondHouseCreate()
    }

    suspend fun read(context: BePsContext, query: PsRequestHouseRead?): PsResponseHouseRead = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("house-read-request-got")
        )
        crud.read(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("house-read-request-handled")
        )
        return respondHouseRead()
    }

    suspend fun update(context: BePsContext, query: PsRequestHouseUpdate?): PsResponseHouseUpdate = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("house-update-request-got")
        )
        crud.update(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("house-update-request-handled")
        )
        return respondHouseUpdate()
    }

    suspend fun delete(context: BePsContext, query: PsRequestHouseDelete?): PsResponseHouseDelete = with(context) {
        query?.also { setQuery(it) }
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("house-delete-request-got")
        )
        crud.delete(this)
        logger.log(
            msg = "Response ready, response = {}",
            level = Level.INFO,
            data = toLog("house-delete-request-handled")
        )
        return respondHouseDelete()
    }
}
