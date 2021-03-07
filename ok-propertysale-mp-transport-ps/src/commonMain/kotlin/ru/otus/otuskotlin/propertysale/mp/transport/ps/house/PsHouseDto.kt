package ru.otus.otuskotlin.propertysale.mp.transport.ps.house

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto

@Serializable
data class PsHouseDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val area: Double? = null,
    val actions: Set<PsActionDto>? = null,
)
