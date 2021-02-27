package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.models

import kotlinx.serialization.Serializable

@Serializable
data class PsUnitTypeDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val synonyms: Set<String>? = null,
    val symbol: String? = null,
    val symbols: Set<String>? = null,
    val isBase: Boolean? = null,
)
