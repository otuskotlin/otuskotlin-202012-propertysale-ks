package ru.otus.otuskotlin.propertysale.be.common.models.flat

data class BePsFlatFilterModel(
    val text: String = ""
) {
    companion object {
        val NONE = BePsFlatFilterModel()
    }
}
