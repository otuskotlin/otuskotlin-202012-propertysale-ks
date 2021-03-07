package ru.otus.otuskotlin.propertysale.mp.transport.ps

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.models.PsHouseCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {

    @Test
    fun serializePsRequestHouseCreateTest() {
        val json = Json {
            prettyPrint = true
        }
        val dto = PsRequestHouseCreate(
            requestId = "house-create-test-id",
            startTime = "2021-03-01T12:00:00",
            createData = PsHouseCreateDto(
                name = "house-test-name-1",
                description = "house test description",
                area = 150.0,
                actions = setOf(
                    PsActionDto(
                        id = "house-test-action-id"
                    )
                )
            )
        )

        val serializedString = json.encodeToString(PsRequestHouseCreate.serializer(), dto)
        println(serializedString)
        assertTrue { serializedString.contains("house-create-test-id") }
        val deserializedDto = json.decodeFromString(PsRequestHouseCreate.serializer(), serializedString)
        assertEquals("house-test-name-1", deserializedDto.createData?.name)
        assertEquals(150.0, deserializedDto.createData?.area)
        assertEquals("house-test-action-id", deserializedDto.createData?.actions?.firstOrNull()?.id)
    }

    @Test
    fun serializePsRequestMessageFlatCreateTest() {
        val jsonRequest = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(PsMessage::class) {
                    subclass(PsRequestFlatCreate::class, PsRequestFlatCreate.serializer())
                }

            }
            classDiscriminator = "type"
        }
        val dto: PsMessage = PsRequestFlatCreate(
            requestId = "flat-create-test-id",
            startTime = "2021-03-01T12:00:00",
            createData = PsFlatCreateDto(
                name = "ps-flat-test-name-1",
                description = "flat test description",
                floor = 5,
                numberOfRooms = 2,
                actions = setOf(
                    PsActionDto(
                        id = "flat-test-action-id"
                    )
                )
            )
        )
        val serializedString = jsonRequest.encodeToString(dto)
        println(serializedString)
        assertTrue { serializedString.contains("flat-create-test-id") }
        val deserializedDto = jsonRequest.decodeFromString(PsMessage.serializer(), serializedString)
        assertEquals(
            "ps-flat-test-name-1",
            (deserializedDto as? PsRequestFlatCreate)?.createData?.name
        )
        assertEquals(
            "flat-test-action-id",
            (deserializedDto as? PsRequestFlatCreate)?.createData?.actions?.firstOrNull()?.id
        )
    }

    @Test
    fun serializePsRequestRoomCreateTest() {
        val json = Json {
            prettyPrint = true
        }
        val dto = PsRequestRoomCreate(
            requestId = "room-create-test-id",
            startTime = "2021-03-01T12:00:00",
            createData = PsRoomCreateDto(
                name = "room-test-name-1",
                description = "room test description",
                length = 7.0,
                width = 5.0,
                actions = setOf(
                    PsActionDto(
                        id = "room-test-action-id"
                    )
                )
            )
        )

        val serializedString = json.encodeToString(PsRequestRoomCreate.serializer(), dto)
        println(serializedString)
        assertTrue { serializedString.contains("room-create-test-id") }
        val deserializedDto = json.decodeFromString(PsRequestRoomCreate.serializer(), serializedString)
        assertEquals("room-test-name-1", deserializedDto.createData?.name)
        assertEquals(7.0, deserializedDto.createData?.length)
        assertEquals(5.0, deserializedDto.createData?.width)
        assertEquals("room-test-action-id", deserializedDto.createData?.actions?.firstOrNull()?.id)
    }
}
