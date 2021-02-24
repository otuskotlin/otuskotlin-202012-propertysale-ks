package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertySaleDetailIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertySaleDetailIdModel("")
    }
}
