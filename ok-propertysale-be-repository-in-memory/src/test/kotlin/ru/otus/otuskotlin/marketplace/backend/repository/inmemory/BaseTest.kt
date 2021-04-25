package ru.otus.otuskotlin.marketplace.backend.repository.inmemory

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.flat.FlatRepoInMemory
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

internal class BaseTest {

    @OptIn(ExperimentalTime::class)
    @Test
    fun createAndGetTest() {
        val repo = FlatRepoInMemory(
            ttl = 5.toDuration(DurationUnit.MINUTES)
        )

        val flat = BePsFlatModel(
            name = "flat-1",
            description = "flat-1-description",
            floor = 5,
            actions = mutableSetOf(
                BePsActionModel(
                    type = "action-1-type",
                    content = "action-1-content",
                    status = "action-1-status"
                )
            )
        )

        val context = BePsContext(
            requestFlat = flat
        )

        runBlocking {
            val createdFlat = repo.create(context)
            assertEquals("flat-1", createdFlat.name)
            assertEquals("flat-1-description", createdFlat.description)
            assertEquals(5, createdFlat.floor)
            assertTrue { createdFlat.actions.isNotEmpty() }
            assertEquals("action-1-type", createdFlat.actions.first().type)
            assertEquals("action-1-content", createdFlat.actions.first().content)
            assertEquals("action-1-status", createdFlat.actions.first().status)
            context.requestFlatId = createdFlat.id

            val readFlat = repo.read(context)
            assertEquals(createdFlat.id, readFlat.id)
            assertEquals("flat-1", readFlat.name)
            assertEquals("flat-1-description", readFlat.description)
            assertEquals(5, readFlat.floor)
            assertTrue { readFlat.actions.isNotEmpty() }
            assertEquals("action-1-type", readFlat.actions.first().type)
            assertEquals("action-1-content", readFlat.actions.first().content)
            assertEquals("action-1-status", readFlat.actions.first().status)
        }
    }
}
