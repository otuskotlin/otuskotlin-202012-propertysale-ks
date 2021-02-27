package ru.otus.otuskotlin.propertysale.be.common.context

import ru.otus.otuskotlin.propertysale.be.common.models.PropertySaleIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.PropertySaleModel

data class PropertySaleBackendContext(
    var requestPropertyId: PropertySaleIdModel = PropertySaleIdModel.NONE,
    var requestProperty: PropertySaleModel = PropertySaleModel.NONE,

    var responseProperty: PropertySaleModel = PropertySaleModel.NONE
)
