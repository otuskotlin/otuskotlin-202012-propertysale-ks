package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsFlatModel(
    var id: BePsFlatIdModel = BePsFlatIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var floor: Int = Int.MIN_VALUE,
    var numberOfRooms: Int = Int.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf()
) {
    companion object {
        val NONE = BePsFlatModel()
    }
}
