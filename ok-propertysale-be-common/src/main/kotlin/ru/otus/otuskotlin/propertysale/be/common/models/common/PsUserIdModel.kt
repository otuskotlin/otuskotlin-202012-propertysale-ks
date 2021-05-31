package ru.otus.otuskotlin.propertysale.be.common.models.common

inline class PsUserIdModel(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = PsUserIdModel("")
    }
}
