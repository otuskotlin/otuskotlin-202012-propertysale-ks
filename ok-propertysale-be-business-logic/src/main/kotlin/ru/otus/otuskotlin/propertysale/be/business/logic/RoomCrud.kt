package ru.otus.otuskotlin.propertysale.be.business.logic

import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomCreate
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomDelete
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomFilter
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomRead
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomUpdate
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext

class RoomCrud {
    suspend fun list(context: BePsContext) {
        RoomFilter.execute(context.apply(this::configureContext))
    }

    suspend fun create(context: BePsContext) {
        RoomCreate.execute(context.apply(this::configureContext))
    }

    suspend fun read(context: BePsContext) {
        RoomRead.execute(context.apply(this::configureContext))
    }

    suspend fun update(context: BePsContext) {
        RoomUpdate.execute(context.apply(this::configureContext))
    }

    suspend fun delete(context: BePsContext) {
        RoomDelete.execute(context.apply(this::configureContext))
    }

    private fun configureContext(context: BePsContext) {

    }
}
