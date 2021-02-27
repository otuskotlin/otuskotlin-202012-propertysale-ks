package ru.otus.otuskotlin.propertysale.mp.transport.ps.common

import kotlinx.serialization.Serializable

@Serializable
enum class PsWorkModeDto {
    PROD,
    TEST
}
