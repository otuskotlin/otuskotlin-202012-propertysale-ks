package ru.otus.otuskotlin.propertysale.be.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatCreate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatDelete
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatList
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatRead
import ru.otus.otuskotlin.propertysale.be.mappers.ps.respondFlatUpdate
import ru.otus.otuskotlin.propertysale.be.mappers.ps.setQuery
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate

private val crud = FlatCrud()

@Post("/flat/list")
fun flatList(): String? = handle { query: PsRequestFlatList? ->
    query?.also { setQuery(it) }
    crud.list(this)
    respondFlatList()
}

@Post("/flat/create")
fun flatCreate(): String? = handle { query: PsRequestFlatCreate? ->
    query?.also { setQuery(it) }
    crud.create(this)
    respondFlatCreate()
}

@Post("/flat/read")
fun flatRead(): String? = handle { query: PsRequestFlatRead? ->
    query?.also { setQuery(it) }
    crud.read(this)
    respondFlatRead()
}

@Post("/flat/update")
fun flatUpdate(): String? = handle { query: PsRequestFlatUpdate? ->
    query?.also { setQuery(it) }
    crud.update(this)
    respondFlatUpdate()
}

@Post("/flat/delete")
fun flatDelete(): String? = handle { query: PsRequestFlatDelete? ->
    query?.also { setQuery(it) }
    crud.delete(this)
    respondFlatDelete()
}
