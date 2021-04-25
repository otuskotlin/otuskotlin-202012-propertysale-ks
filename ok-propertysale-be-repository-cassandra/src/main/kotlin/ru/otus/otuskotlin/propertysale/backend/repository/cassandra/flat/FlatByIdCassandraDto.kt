package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.dto.ActionCassandraDto
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import java.util.*

@Entity
data class FlatByIdCassandraDto(
    @PartitionKey
    @CqlName(ID)
    val id: String? = null,
    @CqlName(NAME)
    val name: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(FLOOR)
    val floor: Int? = null,
    @CqlName(NUMBER_OF_ROOMS)
    val numberOfRooms: Int? = null,
    @CqlName(ACTIONS)
    val actions: Set<ActionCassandraDto>? = null,
    @CqlName(LOCK_VERSION)
    val lockVersion: String? = null,
) {
    fun toModel() = BePsFlatModel(
        id = id?.let { BePsFlatIdModel(it) } ?: BePsFlatModel.NONE.id,
        name = name ?: BePsFlatModel.NONE.name,
        description = description ?: BePsFlatModel.NONE.description,
        floor = floor ?: BePsFlatModel.NONE.floor,
        numberOfRooms = numberOfRooms ?: BePsFlatModel.NONE.numberOfRooms,
        actions = actions?.map { it.toModel() }?.toMutableSet() ?: BePsFlatModel.NONE.actions,
    )

    companion object {
        const val FLATS_TABLE_NAME = "flats_by_id"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val FLOOR = "floor"
        const val NUMBER_OF_ROOMS = "number_of_rooms"
        const val ACTIONS = "actions"
        const val LOCK_VERSION = "lock_version"

        fun of(model: BePsFlatModel) = of(model, model.id.id)

        fun of(model: BePsFlatModel, id: String) = FlatByIdCassandraDto(
            id = id.takeIf { it != BePsFlatModel.NONE.id.id },
            name = model.name.takeIf { it != BePsFlatModel.NONE.name },
            description = model.description.takeIf { it != BePsFlatModel.NONE.description },
            floor = model.floor.takeIf { it != BePsFlatModel.NONE.floor },
            numberOfRooms = model.numberOfRooms.takeIf { it != BePsFlatModel.NONE.numberOfRooms },
            actions = model.actions.takeIf { it != BePsFlatModel.NONE.actions }
                ?.map { ActionCassandraDto.of(it) }
                ?.toSet(),
            lockVersion = UUID.randomUUID().toString(),
        )
    }
}
