package ru.otus.otuskotlin.propertysale.be.business.logic

import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseCreate
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseDelete
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseFilter
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseRead
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseUpdate
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.repositories.IFlatRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository

class HouseCrud(
    private val flatRepoTest: IFlatRepository = IFlatRepository.NONE,
    private val flatRepoProd: IFlatRepository = IFlatRepository.NONE,
    private val houseRepoTest: IHouseRepository = IHouseRepository.NONE,
    private val houseRepoProd: IHouseRepository = IHouseRepository.NONE,
    private val roomRepoTest: IRoomRepository = IRoomRepository.NONE,
    private val roomRepoProd: IRoomRepository = IRoomRepository.NONE,
) {
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
        context.flatRepoTest = flatRepoTest
        context.flatRepoProd = flatRepoProd
        context.houseRepoTest = houseRepoTest
        context.houseRepoProd = houseRepoProd
        context.roomRepoTest = roomRepoTest
        context.roomRepoProd = roomRepoProd
    }
}
