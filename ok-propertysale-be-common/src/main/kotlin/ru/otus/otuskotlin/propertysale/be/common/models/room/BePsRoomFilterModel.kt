package ru.otus.otuskotlin.propertysale.be.common.models.room

import ru.otus.otuskotlin.propertysale.be.common.models.common.PsSortModel

data class BePsRoomFilterModel(
    val text: String = "",
    val includeDescription: Boolean = false,
    val sortBy: PsSortModel = PsSortModel.NONE,
    val offset: Int = Int.MIN_VALUE,
    val count: Int = Int.MIN_VALUE
) {
    companion object {
        val NONE = BePsRoomFilterModel()
    }
}
