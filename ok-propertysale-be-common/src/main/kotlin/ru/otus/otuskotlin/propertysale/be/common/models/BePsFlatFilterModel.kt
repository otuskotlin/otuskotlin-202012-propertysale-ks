package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsFlatFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = BePsFlatFilterModel()
    }
}
