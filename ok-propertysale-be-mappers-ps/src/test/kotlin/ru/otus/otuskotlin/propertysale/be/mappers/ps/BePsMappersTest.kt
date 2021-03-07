package ru.otus.otuskotlin.propertysale.be.mappers.ps

import junit.framework.Assert.assertEquals
import org.junit.Test
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead


internal class BePsMappersTest {

    @Test
    fun flatRequestIdMappingTest() {
        val request: IPsRequest = PsRequestFlatRead(
            id = "flat-test-id"
        )
        val context = BePsContext()

        context.setQuery(request)

        assertEquals("flat-test-id", context.requestFlatId.id)
    }

    @Test
    fun requestFlatCreateMappingTest() {
        val request: IPsRequest = PsRequestFlatCreate(
            createData = PsFlatCreateDto(
                name = "flat-test-name",
                description = "flat-test-description",
                floor = 5,
                numberOfRooms = 2,
                actions = setOf(PsActionDto("flat-test-action-id"))
            )
        )

        val context = BePsContext()

        context.setQuery(request)

        assertEquals("flat-test-name", context.requestFlat.name)
        assertEquals("flat-test-description", context.requestFlat.description)
        assertEquals(5, context.requestFlat.floor)
        assertEquals(2, context.requestFlat.numberOfRooms)
        assertEquals(1, context.requestFlat.actions.size)
        assertEquals("flat-test-action-id", context.requestFlat.actions.first().id.id)
    }

    @Test
    fun houseRequestIdMappingTest() {
        val request: IPsRequest = PsRequestHouseRead(
            id = "house-test-id"
        )
        val context = BePsContext()

        context.setQuery(request)

        assertEquals("house-test-id", context.requestHouseId.id)
    }

    @Test
    fun requestHouseCreateMappingTest() {
        val request: IPsRequest = PsRequestHouseCreate(
            createData = PsHouseCreateDto(
                name = "house-test-name",
                description = "house-test-description",
                area = 150.0,
                actions = setOf(PsActionDto("house-test-action-id"))
            )
        )

        val context = BePsContext()

        context.setQuery(request)

        assertEquals("house-test-name", context.requestHouse.name)
        assertEquals("house-test-description", context.requestHouse.description)
        assertEquals(150.0, context.requestHouse.area)
        assertEquals(1, context.requestHouse.actions.size)
        assertEquals("house-test-action-id", context.requestHouse.actions.first().id.id)
    }

    @Test
    fun roomRequestIdMappingTest() {
        val request: IPsRequest = PsRequestRoomRead(
            id = "room-test-id"
        )
        val context = BePsContext()

        context.setQuery(request)

        assertEquals("room-test-id", context.requestRoomId.id)
    }

    @Test
    fun requestRoomCreateMappingTest() {
        val request: IPsRequest = PsRequestRoomCreate(
            createData = PsRoomCreateDto(
                name = "room-test-name",
                description = "room-test-description",
                length = 7.0,
                width = 5.0,
                actions = setOf(PsActionDto("room-test-action-id"))
            )
        )

        val context = BePsContext()

        context.setQuery(request)

        assertEquals("room-test-name", context.requestRoom.name)
        assertEquals("room-test-description", context.requestRoom.description)
        assertEquals(7.0, context.requestRoom.length)
        assertEquals(5.0, context.requestRoom.width)
        assertEquals(1, context.requestRoom.actions.size)
        assertEquals("room-test-action-id", context.requestRoom.actions.first().id.id)
    }
}
