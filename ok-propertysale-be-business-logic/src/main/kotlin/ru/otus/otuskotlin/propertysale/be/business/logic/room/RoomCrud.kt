package ru.otus.otuskotlin.propertysale.be.business.logic.room

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext

class RoomCrud {
    private val getPipeline = RoomGetPipeline()
    private val createPipeline = RoomCreatePipeline()
    private val updatePipeline = RoomUpdatePipeline()
    private val deletePipeline = RoomDeletePipeline()
    private val filterPipeline = RoomFilterPipeline()

    suspend fun get(context: BePsContext) = getPipeline.run(context)
    suspend fun create(context: BePsContext) = createPipeline.run(context)
    suspend fun update(context: BePsContext) = updatePipeline.run(context)
    suspend fun delete(context: BePsContext) = deletePipeline.run(context)
    suspend fun filter(context: BePsContext) = filterPipeline.run(context)
}
