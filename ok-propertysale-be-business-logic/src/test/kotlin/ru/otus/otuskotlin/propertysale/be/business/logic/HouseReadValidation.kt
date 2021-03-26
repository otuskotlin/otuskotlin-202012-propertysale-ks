package ru.otus.otuskotlin.propertysale.be.business.logic

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house.HouseRead
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class HouseReadValidation {

    @Test
    fun `houseId success non-empty`() {
        val ctx = BePsContext(
            requestHouseId = BePsHouseIdModel("test-house-id")
        )

        runBlocking {
            HouseRead.execute(ctx)
            assertEquals(BePsContextStatus.SUCCESS, ctx.status)
            assertEquals(0, ctx.errors.size)
        }
    }

    @Test
    fun `houseId fails empty`() {
        val ctx = BePsContext(
            requestHouseId = BePsHouseIdModel("")
        )

        runBlocking {
            HouseRead.execute(ctx)
            assertEquals(BePsContextStatus.ERROR, ctx.status)
            assertTrue { ctx.errors.first().message.contains("empty") }
        }
    }
}
