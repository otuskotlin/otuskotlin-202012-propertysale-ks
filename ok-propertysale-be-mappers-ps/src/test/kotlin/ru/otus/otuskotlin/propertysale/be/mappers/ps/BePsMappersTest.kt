package ru.otus.otuskotlin.propertysale.be.mappers.ps

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.crud.PsRequestCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.crud.PsRequestRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsCreateDto
import kotlin.test.Test
import kotlin.test.assertEquals

internal class BePsMappersTest {

    @Test
    fun requestIdMappingTest() {
        val request: IPsRequest = PsRequestRead(
            id = "test-id"
        )
        val context = BePsContext()

        context.setQuery(request)

        assertEquals("test-id", context.requestId.id)
    }

    @Test
    fun requestCreateMappingTest() {
        val request: IPsRequest = PsRequestCreate(
            createData = PsCreateDto(
                title = "test-title",
                description = "test-description",
                tags = setOf("tag1", "tag2")
            )
        )

        val context = BePsContext()

        context.setQuery(request)

        assertEquals("test-title", context.request.title)
        assertEquals(2, context.request.tags.size)
    }
}
