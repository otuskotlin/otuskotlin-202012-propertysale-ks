package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsActionIdModel(
    override val id: String
) : IBePsIdModel {
    companion object {
        val NONE = BePsActionIdModel("")
    }
}
