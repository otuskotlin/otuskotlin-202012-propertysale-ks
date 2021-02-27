package ru.otus.otuskotlin.propertysale.mp.transport.ps.models

import kotlinx.serialization.Serializable

@Serializable
data class PsDetailDto(
    val id: String? = null,
    val param: PsParamDto? = null,
    val textValue: String? = null,
    val unit: PsUnitTypeDto? = null,
    val comparableValue: Double? = null,
)
