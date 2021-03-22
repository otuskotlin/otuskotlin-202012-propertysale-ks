package ru.otus.otuskotlin.propertysale.be.common.models.common

data class BePsActionModel(
    val id: BePsActionIdModel = BePsActionIdModel.NONE,
    val type: String = "",
    val content: String = "",
    val status: String = "",
) {
    companion object {
        val NONE = BePsActionModel()
    }
}
