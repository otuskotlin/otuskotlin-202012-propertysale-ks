package ru.otus.otuskotlin.propertysale.be.common.repositories

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel

interface IFlatRepository {
    suspend fun read(context: BePsContext): BePsFlatModel
    suspend fun list(context: BePsContext): Collection<BePsFlatModel>
    suspend fun create(context: BePsContext): BePsFlatModel
    suspend fun update(context: BePsContext): BePsFlatModel
    suspend fun delete(context: BePsContext): BePsFlatModel

    companion object {
        val NONE = object : IFlatRepository {
            override suspend fun read(context: BePsContext): BePsFlatModel {
                TODO("Not yet implemented")
            }

            override suspend fun list(context: BePsContext): Collection<BePsFlatModel> {
                TODO("Not yet implemented")
            }

            override suspend fun create(context: BePsContext): BePsFlatModel {
                TODO("Not yet implemented")
            }

            override suspend fun update(context: BePsContext): BePsFlatModel {
                TODO("Not yet implemented")
            }

            override suspend fun delete(context: BePsContext): BePsFlatModel {
                TODO("Not yet implemented")
            }
        }
    }
}
