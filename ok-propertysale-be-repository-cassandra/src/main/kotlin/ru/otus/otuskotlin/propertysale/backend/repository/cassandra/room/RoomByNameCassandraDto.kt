package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import java.time.Instant

@Entity
data class RoomByNameCassandraDto(
    @PartitionKey(0)
    @CqlName(NAME)
    val name: String? = null,
    @ClusteringColumn(0)
    @CqlName(TIMESTAMP)
    val timestamp: Instant? = null,
    @ClusteringColumn(1)
    @CqlName(ID)
    val id: String? = null,
    @ClusteringColumn(2)
    @CqlName(NAME_INDEX)
    val nameIndex: String? = null,
) {

    companion object {
        const val ROOMS_NAME_TABLE_NAME = "rooms_by_name"
        const val ID = "id"
        const val NAME = "name"
        const val NAME_INDEX = "name_index"
        const val TIMESTAMP = "timestamp"

        fun of(model: BePsRoomModel) = of(model, model.id.id)

        fun of(model: BePsRoomModel, id: String) = RoomByNameCassandraDto(
            id = id.takeIf { it != BePsRoomModel.NONE.id.id },
            name = model.name.takeIf { it != BePsRoomModel.NONE.name },
            nameIndex = model.name.takeIf { it != BePsRoomModel.NONE.name },
            timestamp = Instant.now()
        )
    }
}
