package ru.otus.otuskotlin.propertysale.mp.transport.ps.crud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsCreateDto

@Serializable
@SerialName("PsRequestCreate")
data class PsRequestCreate(
    override val requestId: String? = null,
    override val debug: Debug? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val createData: PsCreateDto? = null,
) : PsMessage(), IPsRequest {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
