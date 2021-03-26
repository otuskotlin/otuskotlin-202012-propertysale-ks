package ru.otus.otuskotlin.propertysale.be.common.models.common

inline class BePsActionIdModel(
    val id: String
) {
    companion object {
        val NONE = BePsActionIdModel("")
    }
}
