package ru.otus.otuskotlin.propertysale.be.common.context

import ru.otus.otuskotlin.propertysale.be.common.models.PropertyIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.PropertyModel

data class PropertySaleBackendContext(
    var requestPropertyId: PropertyIdModel = PropertyIdModel.NONE,
    var requestProperty: PropertyModel = PropertyModel.NONE
)
