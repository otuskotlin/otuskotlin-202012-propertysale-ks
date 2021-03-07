package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsRoomIdModel(
    val id: String
) {
    companion object {
        val NONE = BePsRoomIdModel("")
    }
}
