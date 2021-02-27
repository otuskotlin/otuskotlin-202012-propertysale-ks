package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsParamIdModel(
    override val id: String
) : IBePsIdModel {
    companion object {
        val NONE = BePsParamIdModel("")
    }
}
