package ru.otus.otuskotlin.propertysale.mp.transport.ps.models

import kotlinx.serialization.Serializable

@Serializable
data class PsListFilterDto(
    val text: String? = null,
)