package ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.models

import kotlinx.serialization.Serializable

@Serializable
data class PsRoomListFilterDto(
    val text: String? = null,
)
