package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.models

import kotlinx.serialization.Serializable

@Serializable
data class PsHouseListFilterDto(
    val text: String? = null,
)
