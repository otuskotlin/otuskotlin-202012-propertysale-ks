package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.room

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel

data class RoomInMemoryDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    var length: Double? = null,
    var width: Double? = null,
    val actions: MutableSet<BePsActionModel>? = null
) {
    fun toModel() = BePsRoomModel(
        id = id?.let { BePsRoomIdModel(it) } ?: BePsRoomIdModel.NONE,
        name = name ?: "",
        description = description ?: "",
        length = length ?: 0.0,
        width = width ?: 0.0,
        actions = actions?.toMutableSet() ?: mutableSetOf(),
    )

    companion object {
        fun of(model: BePsRoomModel) = of(model, model.id.id)

        fun of(model: BePsRoomModel, id: String) = RoomInMemoryDto(
            id = id.takeIf { it.isNotBlank() },
            name = model.name.takeIf { it.isNotBlank() },
            description = model.description.takeIf { it.isNotBlank() },
            length = model.length.takeIf { it != 0.0 },
            width = model.width.takeIf { it != 0.0 },
            actions = model.actions.takeIf { it.isNotEmpty() },
        )
    }
}
