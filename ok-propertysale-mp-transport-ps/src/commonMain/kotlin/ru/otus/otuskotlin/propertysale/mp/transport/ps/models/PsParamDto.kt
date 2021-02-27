package ru.otus.otuskotlin.propertysale.mp.transport.ps.models

import kotlinx.serialization.Serializable

@Serializable
data class PsParamDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priority: Double? = null,
    val units: Set<PsUnitTypeDto>? = null,
)
