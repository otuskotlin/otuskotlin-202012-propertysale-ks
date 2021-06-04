package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.models

import kotlinx.serialization.Serializable

@Serializable
data class PsFlatListFilterDto(
    val text: String? = null,
)
