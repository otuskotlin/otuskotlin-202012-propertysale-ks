package ru.otus.otuskotlin.propertysale.be.common.models

data class PropertySaleActionModel(
    val id: PropertySaleActionIdModel = PropertySaleActionIdModel.NONE,
    val type: String = "",
    val content: String = "",
    val status: String = "",
) {
    companion object {
        val NONE = PropertySaleActionModel()
    }
}
