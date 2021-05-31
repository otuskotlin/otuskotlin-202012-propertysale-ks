package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto

@Serializable
data class PsRoomDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val length: Double? = null,
    val width: Double? = null,
    val actions: Set<PsActionDto>? = null,
)
