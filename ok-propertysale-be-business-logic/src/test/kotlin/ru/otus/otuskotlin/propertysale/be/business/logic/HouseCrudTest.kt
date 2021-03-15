package ru.otus.otuskotlin.propertysale.be.business.logic

import org.junit.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.house.HouseCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest
import kotlin.test.assertEquals

class HouseCrudTest {

    @Test
    fun crudGetTest() {
        val givenCrud = HouseCrud()
        val givenContext = BePsContext(
            requestHouseId = BePsHouseIdModel("house-test-id")
        )
        runBlockingTest {
            givenCrud.get(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsHouseIdModel("house-test-id"), givenContext.responseHouse.id)
        assertEquals("house-test-description", givenContext.responseHouse.description)
        assertEquals("house-test-name", givenContext.responseHouse.name)
        assertEquals(150.0, givenContext.responseHouse.area)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2"))
            ),
            givenContext.responseHouse.actions
        )
    }

    @Test
    fun crudCreateTest() {
        val givenCrud = HouseCrud()
        val givenContext = BePsContext(
            requestHouse = BePsHouseModel(
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1")),
                    BePsActionModel(BePsActionIdModel("2"))
                )
            )
        )
        runBlockingTest {
            givenCrud.create(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsHouseIdModel("house-test-id"), givenContext.responseHouse.id)
        assertEquals("house-test-description", givenContext.responseHouse.description)
        assertEquals("house-test-name", givenContext.responseHouse.name)
        assertEquals(150.0, givenContext.responseHouse.area)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2"))
            ),
            givenContext.responseHouse.actions
        )
    }

    @Test
    fun crudUpdateTest() {
        val givenCrud = HouseCrud()
        val givenContext = BePsContext(
            requestHouse = BePsHouseModel(
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1")),
                    BePsActionModel(BePsActionIdModel("2"))
                )
            )
        )
        runBlockingTest {
            givenCrud.update(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsHouseIdModel("house-test-id"), givenContext.responseHouse.id)
        assertEquals("house-test-description", givenContext.responseHouse.description)
        assertEquals("house-test-name", givenContext.responseHouse.name)
        assertEquals(150.0, givenContext.responseHouse.area)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2"))
            ),
            givenContext.responseHouse.actions
        )
    }

    @Test
    fun crudDeleteTest() {
        val givenCrud = HouseCrud()
        val givenContext = BePsContext(
            requestHouseId = BePsHouseIdModel("house-test-id")
        )
        runBlockingTest {
            givenCrud.delete(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsHouseIdModel("house-test-id"), givenContext.responseHouse.id)
        assertEquals("house-test-description", givenContext.responseHouse.description)
        assertEquals("house-test-name", givenContext.responseHouse.name)
        assertEquals(150.0, givenContext.responseHouse.area)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2"))
            ),
            givenContext.responseHouse.actions
        )
    }

    @Test
    fun crudFilterTest() {
        val givenCrud = HouseCrud()
        val givenContext = BePsContext(
            houseFilter = BePsHouseFilterModel("test-house")
        )
        runBlockingTest {
            givenCrud.filter(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseHouses.size)
        assertEquals(BePsHouseIdModel("house-test-id"), givenContext.responseHouses[0].id)
        assertEquals("house-test-name", givenContext.responseHouses[0].name)
        assertEquals("house-test-description", givenContext.responseHouses[0].description)
        assertEquals(150.0, givenContext.responseHouses[0].area)
        assertEquals(
            setOf(
                BePsActionModel(BePsActionIdModel("1")),
                BePsActionModel(BePsActionIdModel("2"))
            ),
            givenContext.responseHouses[0].actions
        )
    }
}
