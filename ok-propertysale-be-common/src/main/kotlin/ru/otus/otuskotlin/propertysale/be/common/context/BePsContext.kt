package ru.otus.otuskotlin.propertysale.be.common.context

import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomModel

data class BePsContext(
    var status: BePsContextStatus = BePsContextStatus.NONE,

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
