package ru.otus.otuskotlin.propertysale.backend.repository.inmemory.flat

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel

data class FlatInMemoryDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    var floor: Int? = null,
    var numberOfRooms: Int? = null,
    val actions: MutableSet<BePsActionModel>? = null
) {
    fun toModel() = BePsFlatModel(
        id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatIdModel.NONE,
        name = name ?: "",
        description = description ?: "",
        floor = floor ?: 0,
        numberOfRooms = numberOfRooms ?: 0,
        actions = actions?.toMutableSet() ?: mutableSetOf(),
    )

    companion object {
        fun of(model: BePsFlatModel) = of(model, model.id.id)

        fun of(model: BePsFlatModel, id: String) = FlatInMemoryDto(
            id = id.takeIf { it.isNotBlank() },
            name = model.name.takeIf { it.isNotBlank() },
            description = model.description.takeIf { it.isNotBlank() },
            floor = model.floor.takeIf { it != 0 },
            numberOfRooms = model.numberOfRooms.takeIf { it != 0 },
            actions = model.actions.takeIf { it.isNotEmpty() },
        )
    }
}
