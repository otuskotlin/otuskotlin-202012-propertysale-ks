package ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatFilterStub : IOperation<BePsContext> by pipeline({
    startIf { stubCase != PsStubCase.NONE }

    operation {
        startIf { stubCase == PsStubCase.FLAT_LIST_SUCCESS }
        execute {
            responseFlats.add(
                BePsFlatModel(
                    id = BePsFlatIdModel("flat-test-id"),
                    name = "flat-test-name",
                    description = "flat-test-description",
                    floor = 5,
                    numberOfRooms = 2,
                    actions = mutableSetOf(
                        BePsActionModel(BePsActionIdModel("1")),
                        BePsActionModel(BePsActionIdModel("2")),
                        BePsActionModel(BePsActionIdModel("3"))
                    )
                )
            )
            status = BePsContextStatus.FINISHING
        }
    }
})
