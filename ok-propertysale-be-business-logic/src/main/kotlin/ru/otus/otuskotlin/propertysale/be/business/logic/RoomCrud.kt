package ru.otus.otuskotlin.propertysale.be.business.logic

import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomCreate
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomDelete
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomFilter
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomRead
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomUpdate
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.repositories.IFlatRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository

class RoomCrud(
    private val flatRepoTest: IFlatRepository = IFlatRepository.NONE,
    private val flatRepoProd: IFlatRepository = IFlatRepository.NONE,
    private val houseRepoTest: IHouseRepository = IHouseRepository.NONE,
    private val houseRepoProd: IHouseRepository = IHouseRepository.NONE,
    private val roomRepoTest: IRoomRepository = IRoomRepository.NONE,
    private val roomRepoProd: IRoomRepository = IRoomRepository.NONE,
) {
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
        context.flatRepoTest = flatRepoTest
        context.flatRepoProd = flatRepoProd
        context.houseRepoTest = houseRepoTest
        context.houseRepoProd = houseRepoProd
        context.roomRepoTest = roomRepoTest
        context.roomRepoProd = roomRepoProd
    }
}
