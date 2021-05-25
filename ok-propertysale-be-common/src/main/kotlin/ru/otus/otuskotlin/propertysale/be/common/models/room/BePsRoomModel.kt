package ru.otus.otuskotlin.propertysale.be.common.models.room

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsUserModel

data class BePsRoomModel(
    val id: BePsRoomIdModel = BePsRoomIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var length: Double = Double.MIN_VALUE,
    var width: Double = Double.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf(),
    val owner: PsUserModel = PsUserModel.NONE,
) {
    companion object {
        val NONE = BePsRoomModel()
    }
}
