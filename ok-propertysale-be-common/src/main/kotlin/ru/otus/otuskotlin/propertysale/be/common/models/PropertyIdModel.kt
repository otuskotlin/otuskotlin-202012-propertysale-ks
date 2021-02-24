package ru.otus.otuskotlin.propertysale.be.common.models

inline class PropertyIdModel(
    override val id: String
) : IPropertySaleIdModel {
    companion object {
        val NONE = PropertyIdModel("")
    }
}
