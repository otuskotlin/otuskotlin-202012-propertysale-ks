package ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomCreateDto

@Serializable
@SerialName("PsRequestRoomCreate")
data class PsRequestRoomCreate(
    override val requestId: String? = null,
    override val debug: Debug? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val createData: PsRoomCreateDto? = null,
) : PsMessage(), IPsRequest {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto?
    ) : IPsDebug
}
