package ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models.PsRoomListFilterDto

@Serializable
@SerialName("PsRequestRoomList")
data class PsRequestRoomList(
    override val requestId: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: Debug? = null,
    val filterData: PsRoomListFilterDto? = null,
) : IPsRequest, PsMessage() {

    @Serializable
    data class Debug(
        override val mode: PsWorkModeDto? = null,
        val stubCase: StubCase? = null
    ) : IPsDebug

    @Serializable
    enum class StubCase {
        NONE,
        SUCCESS
    }
}
