package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.house.HouseDeleteStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object HouseDelete : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(HouseDeleteStub)

    validation {
        validate<String?> {
            on { requestHouseId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "house-id",
                    message = "House ID requested must not be empty"
                )
            )
        }
    }

    execute(CompletePipeline)
})
