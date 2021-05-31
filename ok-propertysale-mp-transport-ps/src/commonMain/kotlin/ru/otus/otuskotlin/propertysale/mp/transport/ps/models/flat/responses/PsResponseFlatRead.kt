package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.IPsResponse
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.PsFlatDto

@Serializable
@SerialName("PsResponseFlatRead")
data class PsResponseFlatRead(
    override val responseId: String? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val debug: Debug? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val flat: PsFlatDto? = null,
) : IPsResponse, PsMessage() {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
