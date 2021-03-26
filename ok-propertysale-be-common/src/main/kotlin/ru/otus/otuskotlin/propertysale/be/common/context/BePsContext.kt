package ru.otus.otuskotlin.propertysale.be.common.context

import ru.otus.otuskotlin.propertysale.be.common.models.common.IPsError
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import java.time.Instant

data class BePsContext(
    var timeStarted: Instant = Instant.MIN,
    var responseId: String = "",
    var onRequest: String = "",
    var status: BePsContextStatus = BePsContextStatus.NONE,
    var errors: MutableList<IPsError> = mutableListOf(),
    var frameworkErrors: MutableList<Throwable> = mutableListOf(),
    var stubCase: PsStubCase = PsStubCase.NONE,

    var requestFlatId: BePsFlatIdModel = BePsFlatIdModel.NONE,
    var requestFlat: BePsFlatModel = BePsFlatModel.NONE,
    var requestHouseId: BePsHouseIdModel = BePsHouseIdModel.NONE,
    var requestHouse: BePsHouseModel = BePsHouseModel.NONE,
    var requestRoomId: BePsRoomIdModel = BePsRoomIdModel.NONE,
    var requestRoom: BePsRoomModel = BePsRoomModel.NONE,

    var flatFilter: BePsFlatFilterModel = BePsFlatFilterModel.NONE,
    var houseFilter: BePsHouseFilterModel = BePsHouseFilterModel.NONE,
    var roomFilter: BePsRoomFilterModel = BePsRoomFilterModel.NONE,

    var responseFlat: BePsFlatModel = BePsFlatModel.NONE,
    var responseFlats: MutableList<BePsFlatModel> = mutableListOf(),
    var responseHouse: BePsHouseModel = BePsHouseModel.NONE,
    var responseHouses: MutableList<BePsHouseModel> = mutableListOf(),
    var responseRoom: BePsRoomModel = BePsRoomModel.NONE,
    var responseRooms: MutableList<BePsRoomModel> = mutableListOf()
)
