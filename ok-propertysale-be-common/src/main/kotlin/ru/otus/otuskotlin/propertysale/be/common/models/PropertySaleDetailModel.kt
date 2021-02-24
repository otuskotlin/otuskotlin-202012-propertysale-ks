package ru.otus.otuskotlin.propertysale.be.common.models

data class PropertySaleDetailModel(
    val id: PropertySaleDetailIdModel = PropertySaleDetailIdModel.NONE,
    val param: PropertySaleParamModel = PropertySaleParamModel.NONE,
    val textValue: String = "",
    val unit: PropertySaleUnitTypeModel = PropertySaleUnitTypeModel.NONE,
    val comparableValue: Double = Double.NaN
) {
    companion object {
        val NONE = PropertySaleDetailModel()
    }
}
