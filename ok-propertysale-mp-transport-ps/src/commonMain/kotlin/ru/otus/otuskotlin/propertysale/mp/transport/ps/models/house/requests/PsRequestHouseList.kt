package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.IPsDebug
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.IPsRequest
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsWorkModeDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.models.PsHouseListFilterDto

@Serializable
@SerialName("PsRequestHouseList")
data class PsRequestHouseList(
    override val requestId: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: Debug? = null,
    val filterData: PsHouseListFilterDto? = null,
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