package ru.otus.otuskotlin.propertysale.be.business.logic

import org.junit.jupiter.api.Test
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsStubCase
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest
import kotlin.test.assertEquals

class FlatCrudTest {

    @Test
    fun crudGetTest() {
        val givenCrud = FlatCrud()
        val givenContext = BePsContext(
            stubCase = PsStubCase.FLAT_READ_SUCCESS,
            requestFlatId = BePsFlatIdModel("flat-test-id")
        )
        runBlockingTest {
            givenCrud.read(givenContext)
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
            stubCase = PsStubCase.FLAT_CREATE_SUCCESS,
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
            stubCase = PsStubCase.FLAT_UPDATE_SUCCESS,
            requestFlat = BePsFlatModel(
                id = BePsFlatIdModel("flat-test-id"),
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
            stubCase = PsStubCase.FLAT_DELETE_SUCCESS,
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
            stubCase = PsStubCase.FLAT_LIST_SUCCESS,
            flatFilter = BePsFlatFilterModel("test-flat")
        )
        runBlockingTest {
            givenCrud.list(givenContext)
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
