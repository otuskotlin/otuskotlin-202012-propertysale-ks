package ru.otus.otuskotlin.propertysale.mp.transport.ps.logs

import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.PsFlatDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.PsHouseDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.PsRoomDto

data class PsLogModel(
    val requestFlatId: String? = null,
    val requestHouseId: String? = null,
    val requestRoomId: String? = null,
    val requestFlat: PsFlatDto? = null,
    val requestHouse: PsHouseDto? = null,
    val requestRoom: PsRoomDto? = null,
    val responseFlat: PsFlatDto? = null,
    val responseHouse: PsHouseDto? = null,
    val responseRoom: PsRoomDto? = null,
    val responseFlats: List<PsFlatDto>? = null,
    val responseHouses: List<PsHouseDto>? = null,
    val responseRooms: List<PsRoomDto>? = null,
)
