package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsHouseFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = BePsHouseFilterModel()
    }
}
