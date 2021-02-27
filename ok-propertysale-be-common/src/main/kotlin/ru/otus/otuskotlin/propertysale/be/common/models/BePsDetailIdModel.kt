package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsDetailIdModel(
    override val id: String
) : IBePsIdModel {
    companion object {
        val NONE = BePsDetailIdModel("")
    }
}
