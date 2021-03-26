package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat.FlatReadStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatRead : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(FlatReadStub)

    validation {
        validate<String?> {
            on { requestFlatId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "flat-id",
                    message = "Flat ID requested must not be empty"
                )
            )
        }
    }

    execute(CompletePipeline)
})
