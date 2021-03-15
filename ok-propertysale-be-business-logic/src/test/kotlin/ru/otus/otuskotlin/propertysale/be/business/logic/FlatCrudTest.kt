package ru.otus.otuskotlin.propertysale.be.business.logic

import org.junit.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.flat.FlatCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest
import kotlin.test.assertEquals

class FlatCrudTest {

    @Test
    fun crudGetTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            requestFlatId = BePsFlatIdModel("flat-test-id")
        )
        runBlockingTest {
            givenCrud.get(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsFlatIdModel("flat-test-id"), givenContext.responseFlat.id)
        assertEquals("flat-test-description", givenContext.responseFlat.description)
        assertEquals("flat-test-name", givenContext.responseFlat.name)
        assertEquals(5, givenContext.responseFlat.floor)
        assertEquals(2, givenContext.responseFlat.numberOfRooms)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2")),
                BePsActionModel(BePsActionIdModel("3"))
            ),
            givenContext.responseFlat.actions
        )
    }

    @Test
    fun crudCreateTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            requestFlat = BePsFlatModel(
                name = "flat-test-name",
                description = "flat-test-description",
                floor = 5,
                numberOfRooms = 2,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1")),
                    BePsActionModel(BePsActionIdModel("2")),
                    BePsActionModel(BePsActionIdModel("3"))
                )
            )
        )
        runBlockingTest {
            givenCrud.create(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsFlatIdModel("flat-test-id"), givenContext.responseFlat.id)
        assertEquals("flat-test-description", givenContext.responseFlat.description)
        assertEquals("flat-test-name", givenContext.responseFlat.name)
        assertEquals(5, givenContext.responseFlat.floor)
        assertEquals(2, givenContext.responseFlat.numberOfRooms)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2")),
                BePsActionModel(BePsActionIdModel("3"))
            ),
            givenContext.responseFlat.actions
        )
    }

    @Test
    fun crudUpdateTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            requestFlat = BePsFlatModel(
                name = "flat-test-name",
                description = "flat-test-description",
                floor = 5,
                numberOfRooms = 2,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1")),
                    BePsActionModel(BePsActionIdModel("2")),
                    BePsActionModel(BePsActionIdModel("3"))
                )
            )
        )
        runBlockingTest {
            givenCrud.update(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsFlatIdModel("flat-test-id"), givenContext.responseFlat.id)
        assertEquals("flat-test-description", givenContext.responseFlat.description)
        assertEquals("flat-test-name", givenContext.responseFlat.name)
        assertEquals(5, givenContext.responseFlat.floor)
        assertEquals(2, givenContext.responseFlat.numberOfRooms)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2")),
                BePsActionModel(BePsActionIdModel("3"))
            ),
            givenContext.responseFlat.actions
        )
    }

    @Test
    fun crudDeleteTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            requestFlatId = BePsFlatIdModel("flat-test-id")
        )
        runBlockingTest {
            givenCrud.delete(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsFlatIdModel("flat-test-id"), givenContext.responseFlat.id)
        assertEquals("flat-test-description", givenContext.responseFlat.description)
        assertEquals("flat-test-name", givenContext.responseFlat.name)
        assertEquals(5, givenContext.responseFlat.floor)
        assertEquals(2, givenContext.responseFlat.numberOfRooms)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2")),
                BePsActionModel(BePsActionIdModel("3"))
            ),
            givenContext.responseFlat.actions
        )
    }

    @Test
    fun crudFilterTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            flatFilter = BePsFlatFilterModel("test-flat")
        )
        runBlockingTest {
            givenCrud.filter(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseFlats.size)
        assertEquals(BePsFlatIdModel("flat-test-id"), givenContext.responseFlats[0].id)
        assertEquals("flat-test-name", givenContext.responseFlats[0].name)
        assertEquals("flat-test-description", givenContext.responseFlats[0].description)
        assertEquals(5, givenContext.responseFlats[0].floor)
        assertEquals(2, givenContext.responseFlats[0].numberOfRooms)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2")),
                BePsActionModel(BePsActionIdModel("3"))
            ),
            givenContext.responseFlats[0].actions
        )
    }
}
