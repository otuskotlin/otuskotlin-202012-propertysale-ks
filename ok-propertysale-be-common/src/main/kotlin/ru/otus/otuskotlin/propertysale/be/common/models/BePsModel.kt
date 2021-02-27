package ru.otus.otuskotlin.propertysale.be.common.models

data class BePsModel(
    val id: BePsIdModel = BePsIdModel.NONE,
    val title: String = "",
    val description: String = "",
    val tags: MutableSet<String> = mutableSetOf(),
    val details: MutableSet<BePsDetailModel> = mutableSetOf(),
    val actions: MutableSet<BePsActionModel> = mutableSetOf()
) {
    companion object {
        val NONE = BePsModel()
    }
}
