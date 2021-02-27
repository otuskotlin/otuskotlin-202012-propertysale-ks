package ru.otus.otuskotlin.propertysale.mp.transport.ps

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.crud.PsRequestCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsActionDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsCreateDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsDetailDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {

    @Test
    fun serializePsRequestCreateTest() {
        val json = Json {
            prettyPrint = true
        }
        val dto = PsRequestCreate(
            requestId = "create-id",
            startTime = "2021-02-13T12:00:00",
            createData = PsCreateDto(
                title = "ps-1",
                description = "some description",
                details = setOf(
                    PsDetailDto(
                        id = "test-detail-id"
                    )
                ),
                actions = setOf(
                    PsActionDto(
                        id = "test-action-id"
                    )
                )
            )
        )

        val serializedString = json.encodeToString(PsRequestCreate.serializer(), dto)
        println(serializedString)
        assertTrue { serializedString.contains("ps-1") }
        val deserializedDto = json.decodeFromString(PsRequestCreate.serializer(), serializedString)
        assertEquals("test-detail-id", deserializedDto.createData?.details?.firstOrNull()?.id)
        assertEquals("test-action-id", deserializedDto.createData?.actions?.firstOrNull()?.id)
    }

    @Test
    fun serializePsRequestTest() {
        val jsonRequest = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(PsMessage::class) {
                    subclass(PsRequestCreate::class, PsRequestCreate.serializer())
                }

            }
            classDiscriminator = "type"
        }
        val dto: PsMessage = PsRequestCreate(
            requestId = "create-id",
            startTime = "2021-02-13T12:00:00",
            createData = PsCreateDto(
                title = "ps-test-2",
                description = "test description",
                details = setOf(
                    PsDetailDto(
                        id = "test-detail-id-2"
                    )
                ),
                actions = setOf(
                    PsActionDto(
                        id = "test-action-id-2"
                    )
                )
            )
        )
        val serializedString = jsonRequest.encodeToString(dto)
        println(serializedString)
        assertTrue { serializedString.contains("ps-test-2") }
        val deserializedDto = jsonRequest.decodeFromString(PsMessage.serializer(), serializedString)
        assertEquals(
            "test-detail-id-2",
            (deserializedDto as? PsRequestCreate)?.createData?.details?.firstOrNull()?.id
        )
        assertEquals(
            "test-action-id-2",
            (deserializedDto as? PsRequestCreate)?.createData?.actions?.firstOrNull()?.id
        )
    }
}
