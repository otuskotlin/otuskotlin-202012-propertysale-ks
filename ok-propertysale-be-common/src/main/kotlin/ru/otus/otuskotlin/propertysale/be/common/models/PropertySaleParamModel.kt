package ru.otus.otuskotlin.propertysale.be.common.models

data class PropertySaleParamModel(
    var id: PropertySaleParamIdModel = PropertySaleParamIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var priority: Double = Double.MIN_VALUE,
    var units: MutableSet<PropertySaleUnitTypeModel> = mutableSetOf()
) {
    companion object {
        val NONE = PropertySaleParamModel()
    }
}
