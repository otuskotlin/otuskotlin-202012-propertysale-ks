package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat.FlatUpdateStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatUpdate : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(FlatUpdateStub)

    validation {
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "id", message = "You must provide non-empty id for the flat"))
            on { requestFlat.id.asString() }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "name",
                    message = "You must provide non-empty name for the flat"
                )
            )
            on { requestFlat.name }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "description",
                    message = "You must provide non-empty description for the flat"
                )
            )
            on { requestFlat.description }
        }
    }

    execute(CompletePipeline)
})
