package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertySaleActionIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertySaleActionIdModel("")
    }
}
