package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.models

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.models.PsActionDto

@Serializable
data class PsFlatCreateDto(
    val name: String? = null,
    val description: String? = null,
    val floor: Int? = null,
    val numberOfRooms: Int? = null,
    val actions: Set<PsActionDto>? = null,
)
