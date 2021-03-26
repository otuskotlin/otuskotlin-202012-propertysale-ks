package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.room.RoomDeleteStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object RoomDelete : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(RoomDeleteStub)

    validation {
        validate<String?> {
            on { requestRoomId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "room-id",
                    message = "Room ID requested must not be empty"
                )
            )
        }
    }

    execute(CompletePipeline)
})
