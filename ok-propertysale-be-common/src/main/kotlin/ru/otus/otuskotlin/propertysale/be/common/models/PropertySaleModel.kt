package ru.otus.otuskotlin.propertysale.be.common.models

data class PropertySaleModel(
    val id: PropertySaleIdModel = PropertySaleIdModel.NONE,
    val title: String = "",
    val description: String = "",
    val tags: MutableSet<String> = mutableSetOf(),
    val details: MutableSet<PropertySaleDetailModel> = mutableSetOf(),
    val actions: MutableSet<PropertySaleActionModel> = mutableSetOf()
) {
    companion object {
        val NONE = PropertySaleModel()
    }
}
