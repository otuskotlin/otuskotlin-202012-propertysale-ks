package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.crud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.PsWorkModeDto

@Serializable
@SerialName("PsRequestRead")
data class PsRequestRead(
    override val requestId: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: Debug? = null,
    val id: String? = null,
) : IPsRequest, PsMessage() {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
