package ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.models

import kotlinx.serialization.Serializable

@Serializable
data class PsFlatListFilterDto(
    val text: String? = null,
)
