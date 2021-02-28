package ru.otus.otuskotlin.propertysale.mp.transport.ps.models

import kotlinx.serialization.Serializable

@Serializable
data class PsUpdateDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val tags: Set<String>? = null,
    val details: Set<PsDetailDto>? = null,
    val actions: Set<PsActionDto>? = null
)
