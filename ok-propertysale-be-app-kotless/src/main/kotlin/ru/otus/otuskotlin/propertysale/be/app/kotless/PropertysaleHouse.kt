package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondHouseUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseUpdate

private val crud = HouseCrud()

@Post("/house/list")
fun houseList(): String? = handle { query: PsRequestHouseList? ->
    query?.also { setQuery(it) }
    crud.list(this)
    respondHouseList()
}

@Post("/house/create")
fun houseCreate(): String? = handle { query: PsRequestHouseCreate? ->
    query?.also { setQuery(it) }
    crud.create(this)
    respondHouseCreate()
}

@Post("/house/read")
fun houseRead(): String? = handle { query: PsRequestHouseRead? ->
    query?.also { setQuery(it) }
    crud.read(this)
    respondHouseRead()
}

@Post("/house/update")
fun houseUpdate(): String? = handle { query: PsRequestHouseUpdate? ->
    query?.also { setQuery(it) }
    crud.update(this)
    respondHouseUpdate()
}

@Post("/house/delete")
fun houseDelete(): String? = handle { query: PsRequestHouseDelete? ->
    query?.also { setQuery(it) }
    crud.delete(this)
    respondHouseDelete()
}
