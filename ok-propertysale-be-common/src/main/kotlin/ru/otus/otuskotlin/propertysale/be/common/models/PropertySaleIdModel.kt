package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertySaleIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertySaleIdModel("")
    }
}
