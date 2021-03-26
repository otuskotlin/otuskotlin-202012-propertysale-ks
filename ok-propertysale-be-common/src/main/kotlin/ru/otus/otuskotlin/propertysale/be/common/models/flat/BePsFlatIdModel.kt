package ru.otus.otuskotlin.propertysale.be.common.models.flat

inline class BePsFlatIdModel(
    val id: String
) {

    fun asString() = id

    companion object {
        val NONE = BePsFlatIdModel("")
    }
}
