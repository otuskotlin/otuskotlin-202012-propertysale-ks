package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsParamModel(
    var id: BePsParamIdModel = BePsParamIdModel.NONE,
    var name: String = "",
    var description: String = "",
    var priority: Double = Double.MIN_VALUE,
    var units: MutableSet<BePsUnitTypeModel> = mutableSetOf()
) {
    companion object {
        val NONE = BePsParamModel()
    }
}
