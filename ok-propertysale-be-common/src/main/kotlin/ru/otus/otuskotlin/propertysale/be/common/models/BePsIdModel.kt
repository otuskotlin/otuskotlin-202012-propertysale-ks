package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsIdModel(
    override val id: String
) : IBePsIdModel {
    companion object {
        val NONE = BePsIdModel("")
    }
}
