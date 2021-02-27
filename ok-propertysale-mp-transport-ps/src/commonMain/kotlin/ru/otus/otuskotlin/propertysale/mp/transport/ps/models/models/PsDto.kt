package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.models

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.PsItemPermission

@Serializable
data class PsDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val tags: Set<String>? = null,
    val details: Set<PsDetailDto>? = null,
    val actions: Set<PsActionDto>? = null,
    val permissions: Set<PsItemPermission>? = null,
)
