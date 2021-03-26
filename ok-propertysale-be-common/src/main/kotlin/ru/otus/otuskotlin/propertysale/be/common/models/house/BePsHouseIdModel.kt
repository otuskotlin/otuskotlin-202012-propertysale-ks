package ru.otus.otuskotlin.propertysale.be.common.models.house

inline class BePsHouseIdModel(
    val id: String
) {

    fun asString() = id

    companion object {
        val NONE = BePsHouseIdModel("")
    }
}
