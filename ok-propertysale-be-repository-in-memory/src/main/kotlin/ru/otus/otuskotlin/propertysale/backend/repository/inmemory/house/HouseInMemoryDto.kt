package ru.otus.otuskotlin.propertysale.backend.repository.inmemory.house

import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel

data class HouseInMemoryDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    var area: Double? = null,
    val actions: MutableSet<BePsActionModel>? = null
) {
    fun toModel() = BePsHouseModel(
        id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseIdModel.NONE,
        name = name ?: "",
        description = description ?: "",
        area = area ?: 0.0,
        actions = actions?.toMutableSet() ?: mutableSetOf(),
    )

    companion object {
        fun of(model: BePsHouseModel) = of(model, model.id.id)

        fun of(model: BePsHouseModel, id: String) = HouseInMemoryDto(
            id = id.takeIf { it.isNotBlank() },
            name = model.name.takeIf { it.isNotBlank() },
            description = model.description.takeIf { it.isNotBlank() },
            area = model.area.takeIf { it != 0.0 },
            actions = model.actions.takeIf { it.isNotEmpty() },
        )
    }
}
