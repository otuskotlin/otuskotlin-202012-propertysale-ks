package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.house.HouseCreateStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object HouseCreate : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(HouseCreateStub)

    validation {
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "name",
                    message = "You must provide non-empty name for the house"
                )
            )
            on { requestHouse.name }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "description",
                    message = "You must provide non-empty description for the demand"
                )
            )
            on { requestHouse.description }
        }
    }

    execute(CompletePipeline)
})
