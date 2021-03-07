package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsHouseModel(
    val id: BePsHouseIdModel = BePsHouseIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var area: Double = Double.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf()
) {
    companion object {
        val NONE = BePsHouseModel()
    }
}
