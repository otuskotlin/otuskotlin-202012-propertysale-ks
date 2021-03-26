package ru.otus.otuskotlin.propertysale.be.business.logic

import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseCreate
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseDelete
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseFilter
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseRead
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseUpdate
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext

class HouseCrud {
    suspend fun list(context: BePsContext) {
        HouseFilter.execute(context.apply(this::configureContext))
    }

    suspend fun create(context: BePsContext) {
        HouseCreate.execute(context.apply(this::configureContext))
    }

    suspend fun read(context: BePsContext) {
        HouseRead.execute(context.apply(this::configureContext))
    }

    suspend fun update(context: BePsContext) {
        HouseUpdate.execute(context.apply(this::configureContext))
    }

    suspend fun delete(context: BePsContext) {
        HouseDelete.execute(context.apply(this::configureContext))
    }

    private fun configureContext(context: BePsContext) {

    }
}
