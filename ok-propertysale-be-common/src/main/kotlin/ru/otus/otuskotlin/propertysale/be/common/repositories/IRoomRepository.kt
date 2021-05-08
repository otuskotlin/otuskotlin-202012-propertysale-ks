package ru.otus.otuskotlin.propertysale.be.common.repositories

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel

interface IRoomRepository {
    suspend fun read(context: BePsContext): BePsRoomModel
    suspend fun list(context: BePsContext): Collection<BePsRoomModel>
    suspend fun create(context: BePsContext): BePsRoomModel
    suspend fun update(context: BePsContext): BePsRoomModel
    suspend fun delete(context: BePsContext): BePsRoomModel

    companion object {
        val NONE = object : IRoomRepository {
            override suspend fun read(context: BePsContext): BePsRoomModel {
                TODO("Not yet implemented")
            }

            override suspend fun list(context: BePsContext): Collection<BePsRoomModel> {
                TODO("Not yet implemented")
            }

            override suspend fun create(context: BePsContext): BePsRoomModel {
                TODO("Not yet implemented")
            }

            override suspend fun update(context: BePsContext): BePsRoomModel {
                TODO("Not yet implemented")
            }

            override suspend fun delete(context: BePsContext): BePsRoomModel {
                TODO("Not yet implemented")
            }
        }
    }
}
