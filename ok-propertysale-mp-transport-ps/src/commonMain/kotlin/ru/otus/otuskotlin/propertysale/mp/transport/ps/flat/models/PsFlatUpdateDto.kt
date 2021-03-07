package ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.models.PsActionDto

@Serializable
data class PsFlatUpdateDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val floor: Int? = null,
    val numberOfRooms: Int? = null,
    val actions: Set<PsActionDto>? = null,
)
