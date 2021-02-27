package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.models

import kotlinx.serialization.Serializable

@Serializable
data class PsActionDto(
    val id: String? = null,
    val type: String? = null,
    val content: String? = null,
    val status: String? = null,
)
