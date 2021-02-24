package ru.otus.otuskotlin.propertysale.be.common.models

interface IPropertySaleItemModel {
    val id: IPropertySaleIdModel
    val title: String
    val description: String
    val tags: MutableSet<String>
    val details: MutableSet<PropertySaleDetailModel>
}
