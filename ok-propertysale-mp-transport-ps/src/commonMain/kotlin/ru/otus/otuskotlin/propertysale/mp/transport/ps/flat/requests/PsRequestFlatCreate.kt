package ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models.PsFlatCreateDto

@Serializable
@SerialName("PsRequestFlatCreate")
data class PsRequestFlatCreate(
    override val requestId: String? = null,
    override val debug: Debug? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val createData: PsFlatCreateDto? = null,
) : PsMessage(), IPsRequest {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
