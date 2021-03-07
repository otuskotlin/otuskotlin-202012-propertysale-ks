package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsFlatIdModel(
    val id: String
) {
    companion object {
        val NONE = BePsFlatIdModel("")
    }
}
