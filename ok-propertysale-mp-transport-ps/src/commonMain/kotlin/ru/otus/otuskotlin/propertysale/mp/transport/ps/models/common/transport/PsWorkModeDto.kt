package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport

import kotlinx.serialization.Serializable

@Serializable
enum class PsWorkModeDto {
    PROD,
    TEST
}
