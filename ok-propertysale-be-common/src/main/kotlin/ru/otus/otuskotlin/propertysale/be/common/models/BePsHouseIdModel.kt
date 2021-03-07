package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsHouseIdModel(
    val id: String
) {
    companion object {
        val NONE = BePsHouseIdModel("")
    }
}
