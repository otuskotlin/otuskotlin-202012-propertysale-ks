package ru.otus.otuskotlin.propertysale.be.business.logic

import org.junit.Test
import ru.otus.otuskotlin.propertysale.be.business.logic.room.RoomCrud
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomFilterModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest
import kotlin.test.assertEquals

class RoomCrudTest {

    @Test
    fun crudGetTest() {
        val givenCrud = RoomCrud()
        val givenContext = BePsContext(
            requestRoomId = BePsRoomIdModel("room-test-id")
        )
        runBlockingTest {
            givenCrud.get(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsRoomIdModel("room-test-id"), givenContext.responseRoom.id)
        assertEquals("room-test-description", givenContext.responseRoom.description)
        assertEquals("room-test-name", givenContext.responseRoom.name)
        assertEquals(7.0, givenContext.responseRoom.length)
        assertEquals(5.0, givenContext.responseRoom.width)
        assertEquals(setOf(BePsActionModel(BePsActionIdModel("1"))), givenContext.responseRoom.actions)
    }

    @Test
    fun crudCreateTest() {
        val givenCrud = RoomCrud()
        val givenContext = BePsContext(
            requestRoom = BePsRoomModel(
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1"))
                )
            )
        )
        runBlockingTest {
            givenCrud.create(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsRoomIdModel("room-test-id"), givenContext.responseRoom.id)
        assertEquals("room-test-description", givenContext.responseRoom.description)
        assertEquals("room-test-name", givenContext.responseRoom.name)
        assertEquals(7.0, givenContext.responseRoom.length)
        assertEquals(5.0, givenContext.responseRoom.width)
        assertEquals(setOf(BePsActionModel(BePsActionIdModel("1"))), givenContext.responseRoom.actions)
    }

    @Test
    fun crudUpdateTest() {
        val givenCrud = RoomCrud()
        val givenContext = BePsContext(
            requestRoom = BePsRoomModel(
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = mutableSetOf(
                    BePsActionModel(BePsActionIdModel("1"))
                )
            )
        )
        runBlockingTest {
            givenCrud.update(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsRoomIdModel("room-test-id"), givenContext.responseRoom.id)
        assertEquals("room-test-description", givenContext.responseRoom.description)
        assertEquals("room-test-name", givenContext.responseRoom.name)
        assertEquals(7.0, givenContext.responseRoom.length)
        assertEquals(5.0, givenContext.responseRoom.width)
        assertEquals(setOf(BePsActionModel(BePsActionIdModel("1"))), givenContext.responseRoom.actions)
    }

    @Test
    fun crudDeleteTest() {
        val givenCrud = RoomCrud()
        val givenContext = BePsContext(
            requestRoomId = BePsRoomIdModel("room-test-id")
        )
        runBlockingTest {
            givenCrud.delete(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(BePsRoomIdModel("room-test-id"), givenContext.responseRoom.id)
        assertEquals("room-test-description", givenContext.responseRoom.description)
        assertEquals("room-test-name", givenContext.responseRoom.name)
        assertEquals(7.0, givenContext.responseRoom.length)
        assertEquals(5.0, givenContext.responseRoom.width)
        assertEquals(setOf(BePsActionModel(BePsActionIdModel("1"))), givenContext.responseRoom.actions)
    }

    @Test
    fun crudFilterTest() {
        val givenCrud = RoomCrud()
        val givenContext = BePsContext(
            roomFilter = BePsRoomFilterModel("test-room")
        )
        runBlockingTest {
            givenCrud.filter(givenContext)
        }
        assertEquals(BePsContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseRooms.size)
        assertEquals(BePsRoomIdModel("room-test-id"), givenContext.responseRooms[0].id)
        assertEquals("room-test-name", givenContext.responseRooms[0].name)
        assertEquals("room-test-description", givenContext.responseRooms[0].description)
        assertEquals(7.0, givenContext.responseRooms[0].length)
        assertEquals(5.0, givenContext.responseRooms[0].width)
        assertEquals(setOf(BePsActionModel(BePsActionIdModel("1"))), givenContext.responseRooms[0].actions)
    }
}
