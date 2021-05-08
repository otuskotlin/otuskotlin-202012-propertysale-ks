package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.dto.ActionCassandraDto
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import java.util.*

@Entity
data class HouseByIdCassandraDto(
    @PartitionKey
    @CqlName(ID)
    val id: String? = null,
    @CqlName(NAME)
    val name: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(AREA)
    val area: Double? = null,
    @CqlName(ACTIONS)
    val actions: Set<ActionCassandraDto>? = null,
    @CqlName(LOCK_VERSION)
    val lockVersion: String? = null,
) {
    fun toModel() = BePsHouseModel(
        id = id?.let { BePsHouseIdModel(it) } ?: BePsHouseModel.NONE.id,
        name = name ?: BePsHouseModel.NONE.name,
        description = description ?: BePsHouseModel.NONE.description,
        area = area ?: BePsHouseModel.NONE.area,
        actions = actions?.map { it.toModel() }?.toMutableSet() ?: BePsHouseModel.NONE.actions,
    )

    companion object {
        const val HOUSES_TABLE_NAME = "houses_by_id"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val AREA = "area"
        const val ACTIONS = "actions"
        const val LOCK_VERSION = "lock_version"

        fun of(model: BePsHouseModel) = of(model, model.id.id)

        fun of(model: BePsHouseModel, id: String) = HouseByIdCassandraDto(
            id = id.takeIf { it != BePsHouseModel.NONE.id.id },
            name = model.name.takeIf { it != BePsHouseModel.NONE.name },
            description = model.description.takeIf { it != BePsHouseModel.NONE.description },
            area = model.area.takeIf { it != BePsHouseModel.NONE.area },
            actions = model.actions.takeIf { it != BePsHouseModel.NONE.actions }
                ?.map { ActionCassandraDto.of(it) }
                ?.toSet(),
            lockVersion = UUID.randomUUID().toString(),
        )
    }
}
