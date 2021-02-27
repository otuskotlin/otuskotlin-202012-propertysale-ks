package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsDetailModel(
    val id: BePsDetailIdModel = BePsDetailIdModel.NONE,
    val param: BePsParamModel = BePsParamModel.NONE,
    val textValue: String = "",
    val unit: BePsUnitTypeModel = BePsUnitTypeModel.NONE,
    val comparableValue: Double = Double.NaN
) {
    companion object {
        val NONE = BePsDetailModel()
    }
}
