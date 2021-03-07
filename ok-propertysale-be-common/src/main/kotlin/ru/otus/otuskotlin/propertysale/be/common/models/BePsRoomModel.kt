package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsRoomModel(
    val id: BePsRoomIdModel = BePsRoomIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var length: Double = Double.MIN_VALUE,
    var width: Double = Double.MIN_VALUE,
    val actions: MutableSet<BePsActionModel> = mutableSetOf()
) {
    companion object {
        val NONE = BePsRoomModel()
    }
}
