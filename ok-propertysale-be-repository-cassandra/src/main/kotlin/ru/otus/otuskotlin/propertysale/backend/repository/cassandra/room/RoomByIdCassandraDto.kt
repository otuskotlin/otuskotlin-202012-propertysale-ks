package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.dto.ActionCassandraDto
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import java.util.*

@Entity
data class RoomByIdCassandraDto(
    @PartitionKey
    @CqlName(ID)
    val id: String? = null,
    @CqlName(NAME)
    val name: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(LENGTH)
    val length: Double? = null,
    @CqlName(WIDTH)
    val width: Double? = null,
    @CqlName(ACTIONS)
    val actions: Set<ActionCassandraDto>? = null,
    @CqlName(LOCK_VERSION)
    val lockVersion: String? = null,
) {
    fun toModel() = BePsRoomModel(
        id = id?.let { BePsRoomIdModel(it) } ?: BePsRoomModel.NONE.id,
        name = name ?: BePsRoomModel.NONE.name,
        description = description ?: BePsRoomModel.NONE.description,
        length = length ?: BePsRoomModel.NONE.length,
        width = width ?: BePsRoomModel.NONE.width,
        actions = actions?.map { it.toModel() }?.toMutableSet() ?: BePsRoomModel.NONE.actions,
    )

    companion object {
        const val ROOMS_TABLE_NAME = "rooms_by_id"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val LENGTH = "length"
        const val WIDTH = "width"
        const val ACTIONS = "actions"
        const val LOCK_VERSION = "lock_version"

        fun of(model: BePsRoomModel) = of(model, model.id.id)

        fun of(model: BePsRoomModel, id: String) = RoomByIdCassandraDto(
            id = id.takeIf { it != BePsRoomModel.NONE.id.id },
            name = model.name.takeIf { it != BePsRoomModel.NONE.name },
            description = model.description.takeIf { it != BePsRoomModel.NONE.description },
            length = model.length.takeIf { it != BePsRoomModel.NONE.length },
            width = model.width.takeIf { it != BePsRoomModel.NONE.width },
            actions = model.actions.takeIf { it != BePsRoomModel.NONE.actions }
                ?.map { ActionCassandraDto.of(it) }
                ?.toSet(),
            lockVersion = UUID.randomUUID().toString(),
        )
    }
}
