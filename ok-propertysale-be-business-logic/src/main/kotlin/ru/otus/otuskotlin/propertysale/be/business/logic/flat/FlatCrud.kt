package ru.otus.otuskotlin.propertysale.be.business.logic.flat

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext

class FlatCrud {
    private val getPipeline = FlatGetPipeline()
    private val createPipeline = FlatCreatePipeline()
    private val updatePipeline = FlatUpdatePipeline()
    private val deletePipeline = FlatDeletePipeline()
    private val filterPipeline = FlatFilterPipeline()

    suspend fun get(context: BePsContext) = getPipeline.run(context)
    suspend fun create(context: BePsContext) = createPipeline.run(context)
    suspend fun update(context: BePsContext) = updatePipeline.run(context)
    suspend fun delete(context: BePsContext) = deletePipeline.run(context)
    suspend fun filter(context: BePsContext) = filterPipeline.run(context)
}
