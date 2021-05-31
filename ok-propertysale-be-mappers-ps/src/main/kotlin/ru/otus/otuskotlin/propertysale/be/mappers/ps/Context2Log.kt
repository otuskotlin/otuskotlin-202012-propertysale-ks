package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.logs.CommonLogModel
import ru.otus.otuskotlin.propertysale.mp.transport.ps.logs.PsLogModel
import java.time.Instant
import java.util.*

fun BePsContext.toLog(logId: String) = CommonLogModel(
    messageId = UUID.randomUUID().toString(),
    messageTime = Instant.now().toString(),
    source = "ok-propertysale",
    logId = logId,
    propertysale = PsLogModel(
        requestFlatId = requestFlatId.takeIf { it != BePsFlatIdModel.NONE }?.asString(),
        requestHouseId = requestHouseId.takeIf { it != BePsHouseIdModel.NONE }?.asString(),
        requestRoomId = requestRoomId.takeIf { it != BePsRoomIdModel.NONE }?.asString(),
        requestFlat = requestFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
        requestHouse = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
        requestRoom = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
        responseFlat = responseFlat.takeIf { it != BePsFlatModel.NONE }?.toTransport(),
        responseHouse = responseHouse.takeIf { it != BePsHouseModel.NONE }?.toTransport(),
        responseRoom = responseRoom.takeIf { it != BePsRoomModel.NONE }?.toTransport(),
        responseFlats = responseFlats.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
        responseHouses = responseHouses.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
        responseRooms = responseRooms.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    ),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
)
