package ru.otus.otuskotlin.propertysale.mp.transport.ps.crud

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.ErrorDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.IPsResponse
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.PsDto

@Serializable
@SerialName("PsResponseList")
data class PsResponseList(
    override val responseId: String? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    override val debug: Debug? = null,
    val properties: List<PsDto>? = null,
) : IPsResponse, PsMessage() {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
