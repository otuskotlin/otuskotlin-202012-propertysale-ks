package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertySaleParamIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertySaleParamIdModel("")
    }
}
