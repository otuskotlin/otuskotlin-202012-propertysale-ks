package ru.otus.otuskotlin.propertysale.be.common.repositories

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel

interface IHouseRepository {
    suspend fun read(context: BePsContext): BePsHouseModel
    suspend fun list(context: BePsContext): Collection<BePsHouseModel>
    suspend fun create(context: BePsContext): BePsHouseModel
    suspend fun update(context: BePsContext): BePsHouseModel
    suspend fun delete(context: BePsContext): BePsHouseModel

    companion object {
        val NONE = object : IHouseRepository {
            override suspend fun read(context: BePsContext): BePsHouseModel {
                TODO("Not yet implemented")
            }

            override suspend fun list(context: BePsContext): Collection<BePsHouseModel> {
                TODO("Not yet implemented")
            }

            override suspend fun create(context: BePsContext): BePsHouseModel {
                TODO("Not yet implemented")
            }

            override suspend fun update(context: BePsContext): BePsHouseModel {
                TODO("Not yet implemented")
            }

            override suspend fun delete(context: BePsContext): BePsHouseModel {
                TODO("Not yet implemented")
            }
        }
    }
}
