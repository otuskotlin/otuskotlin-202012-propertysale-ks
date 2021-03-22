package ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.house

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object HouseCreateStub : IOperation<BePsContext> by pipeline({
    startIf { stubCase != PsStubCase.NONE }

    operation {
        startIf { stubCase == PsStubCase.HOUSE_CREATE_SUCCESS }
        execute {
            responseHouse = BePsHouseModel(
                id = BePsHouseIdModel("house-test-id"),
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1")),
                    BePsActionModel(BePsActionIdModel("2"))
                )
            )
            status = BePsContextStatus.FINISHING
        }
    }
})
