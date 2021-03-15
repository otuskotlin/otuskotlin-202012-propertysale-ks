package ru.otus.otuskotlin.propertysale.be.business.logic.house

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext

class HouseCrud {
    private val getPipeline = HouseGetPipeline()
    private val createPipeline = HouseCreatePipeline()
    private val updatePipeline = HouseUpdatePipeline()
    private val deletePipeline = HouseDeletePipeline()
    private val filterPipeline = HouseFilterPipeline()

    suspend fun get(context: BePsContext) = getPipeline.run(context)
    suspend fun create(context: BePsContext) = createPipeline.run(context)
    suspend fun update(context: BePsContext) = updatePipeline.run(context)
    suspend fun delete(context: BePsContext) = deletePipeline.run(context)
    suspend fun filter(context: BePsContext) = filterPipeline.run(context)
}
