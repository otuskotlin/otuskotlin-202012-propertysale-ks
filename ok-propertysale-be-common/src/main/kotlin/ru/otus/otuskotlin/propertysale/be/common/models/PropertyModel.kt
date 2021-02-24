package ru.otus.otuskotlin.propertysale.be.common.models

data class PropertyModel(
    override val id: PropertyIdModel = PropertyIdModel.NONE,
    override val title: String = "",
    override val description: String = "",
    override val tags: MutableSet<String> = mutableSetOf(),
    override val details: MutableSet<PropertySaleDetailModel> = mutableSetOf()
) : IPropertySaleItemModel {
    companion object {
        val NONE = PropertyModel()
    }
}
