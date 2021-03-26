package ru.otus.otuskotlin.propertysale.be.common.models.room

inline class BePsRoomIdModel(
    val id: String
) {

    fun asString() = id

    companion object {
        val NONE = BePsRoomIdModel("")
    }
}
