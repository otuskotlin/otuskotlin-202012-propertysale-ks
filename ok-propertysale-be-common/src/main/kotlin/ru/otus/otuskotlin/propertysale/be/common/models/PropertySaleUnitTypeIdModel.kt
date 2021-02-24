package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertySaleUnitTypeIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertySaleUnitTypeIdModel("")
    }
}
