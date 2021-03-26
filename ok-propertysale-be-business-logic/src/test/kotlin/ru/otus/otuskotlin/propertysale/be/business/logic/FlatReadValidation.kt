package ru.otus.otuskotlin.propertysale.be.business.logic

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat.FlatRead
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class FlatReadValidation {

    @Test
    fun `flatId success non-empty`() {
        val ctx = BePsContext(
            requestFlatId = BePsFlatIdModel("test-flat-id")
        )

        runBlocking {
            FlatRead.execute(ctx)
            assertEquals(BePsContextStatus.SUCCESS, ctx.status)
            assertEquals(0, ctx.errors.size)
        }
    }

    @Test
    fun `flatId fails empty`() {
        val ctx = BePsContext(
            requestFlatId = BePsFlatIdModel("")
        )

        runBlocking {
            FlatRead.execute(ctx)
            assertEquals(BePsContextStatus.ERROR, ctx.status)
            assertTrue { ctx.errors.first().message.contains("empty") }
        }
    }
}
