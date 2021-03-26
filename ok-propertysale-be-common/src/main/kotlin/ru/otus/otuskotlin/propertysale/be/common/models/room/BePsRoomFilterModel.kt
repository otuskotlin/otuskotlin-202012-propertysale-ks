package ru.otus.otuskotlin.propertysale.be.common.models.room

data class BePsRoomFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = BePsRoomFilterModel()
    }
}
