package ru.otus.otuskotlin.propertysale.be.common.models.house

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsUserModel

data class BePsHouseModel(
    val id: BePsHouseIdModel = BePsHouseIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var area: Double = Double.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf(),
    val owner: PsUserModel = PsUserModel.NONE,
) {
    companion object {
        val NONE = BePsHouseModel()
    }
}
