package ru.otus.otuskotlin.propertysale.be.common.context

import ru.otus.otuskotlin.propertysale.be.common.models.BePsIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsModel

data class BePsContext(
    var requestId: BePsIdModel = BePsIdModel.NONE,
    var request: BePsModel = BePsModel.NONE,

    var response: BePsModel = BePsModel.NONE
)
