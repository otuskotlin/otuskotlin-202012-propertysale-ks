package ru.otus.otuskotlin.propertysale.be.common.models.flat

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsUserModel

data class BePsFlatModel(
    var id: BePsFlatIdModel = BePsFlatIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var floor: Int = Int.MIN_VALUE,
    var numberOfRooms: Int = Int.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf(),
    val owner: PsUserModel = PsUserModel.NONE,
) {
    companion object {
        val NONE = BePsFlatModel()
    }
}
