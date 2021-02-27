package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsUnitTypeModel(
    val id: BePsUnitTypeIdModel = BePsUnitTypeIdModel.NONE,
    val name: String = "",
    val description: String = "",
    val synonyms: MutableSet<String> = mutableSetOf(),
    val symbol: String = "",
    val symbols: MutableSet<String> = mutableSetOf(),
    val isBase: Boolean = false,
) {
    companion object {
        val NONE = BePsUnitTypeModel()
    }
}
