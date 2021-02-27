package ru.otus.otuskotlin.propertysale.be.common.models

inline class BePsUnitTypeIdModel(
    override val id: String
) : IBePsIdModel {
    companion object {
        val NONE = BePsUnitTypeIdModel("")
    }
}
