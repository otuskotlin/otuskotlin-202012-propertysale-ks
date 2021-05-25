package ru.otus.otuskotlin.propertysale.be.business.logic

import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatCreate
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatDelete
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatFilter
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatRead
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatUpdate
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.repositories.IFlatRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository

class FlatCrud(
    private val flatRepoTest: IFlatRepository = IFlatRepository.NONE,
    private val flatRepoProd: IFlatRepository = IFlatRepository.NONE,
    private val houseRepoTest: IHouseRepository = IHouseRepository.NONE,
    private val houseRepoProd: IHouseRepository = IHouseRepository.NONE,
    private val roomRepoTest: IRoomRepository = IRoomRepository.NONE,
    private val roomRepoProd: IRoomRepository = IRoomRepository.NONE,
) {
    suspend fun list(context: BePsContext) {
        FlatFilter.execute(context.apply(this::configureContext))
    }

    suspend fun create(context: BePsContext) {
        FlatCreate.execute(context.apply(this::configureContext))
    }

    suspend fun read(context: BePsContext) {
        FlatRead.execute(context.apply(this::configureContext))
    }

    suspend fun update(context: BePsContext) {
        FlatUpdate.execute(context.apply(this::configureContext))
    }

    suspend fun delete(context: BePsContext) {
        FlatDelete.execute(context.apply(this::configureContext))
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
