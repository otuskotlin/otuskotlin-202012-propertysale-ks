package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.room.RoomUpdateStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object RoomUpdate : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(RoomUpdateStub)

    validation {
        validate<String?> {
            validator(ValidatorStringNonEmpty(field = "id", message = "You must provide non-empty id for the room"))
            on { requestRoom.id.asString() }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "name",
                    message = "You must provide non-empty name for the room"
                )
            )
            on { requestRoom.name }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "description",
                    message = "You must provide non-empty description for the room"
                )
            )
            on { requestRoom.description }
        }
    }

    execute(CompletePipeline)
})
