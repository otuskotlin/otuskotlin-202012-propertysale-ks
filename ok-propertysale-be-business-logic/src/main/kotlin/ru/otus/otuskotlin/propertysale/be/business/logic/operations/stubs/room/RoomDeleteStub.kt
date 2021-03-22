package ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.room

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object RoomDeleteStub : IOperation<BePsContext> by pipeline({
    startIf { stubCase != PsStubCase.NONE }

    operation {
        startIf { stubCase == PsStubCase.ROOM_DELETE_SUCCESS }
        execute {
            responseRoom = BePsRoomModel(
                id = BePsRoomIdModel("room-test-id"),
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1"))
                )
            )
            status = BePsContextStatus.FINISHING
        }
    }
})
