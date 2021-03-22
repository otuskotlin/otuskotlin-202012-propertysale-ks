package ru.otus.otuskotlin.propertysale.be.business.logic

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room.RoomRead
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RoomReadValidation {

    @Test
    fun `roomId success non-empty`() {
        val ctx = BePsContext(
            requestRoomId = BePsRoomIdModel("test-room-id")
        )

        runBlocking {
            RoomRead.execute(ctx)
            assertEquals(BePsContextStatus.SUCCESS, ctx.status)
            assertEquals(0, ctx.errors.size)
        }
    }

    @Test
    fun `roomId fails empty`() {
        val ctx = BePsContext(
            requestRoomId = BePsRoomIdModel("")
        )

        runBlocking {
            RoomRead.execute(ctx)
            assertEquals(BePsContextStatus.ERROR, ctx.status)
            assertTrue { ctx.errors.first().message.contains("empty") }
        }
    }
}
