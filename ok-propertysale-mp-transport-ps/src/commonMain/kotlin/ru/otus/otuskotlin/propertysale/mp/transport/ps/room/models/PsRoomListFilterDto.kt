package ru.otus.otuskotlin.propertysale.mp.transport.ps.room.models

import kotlinx.serialization.Serializable

@Serializable
data class PsRoomListFilterDto(
    val text: String? = null,
)
