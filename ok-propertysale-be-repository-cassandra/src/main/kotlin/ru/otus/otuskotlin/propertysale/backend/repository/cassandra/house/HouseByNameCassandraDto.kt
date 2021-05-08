package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import java.time.Instant

@Entity
data class HouseByNameCassandraDto(
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
        const val HOUSES_NAME_TABLE_NAME = "houses_by_name"
        const val ID = "id"
        const val NAME = "name"
        const val NAME_INDEX = "name_index"
        const val TIMESTAMP = "timestamp"

        fun of(model: BePsHouseModel) = of(model, model.id.id)

        fun of(model: BePsHouseModel, id: String) = HouseByNameCassandraDto(
            id = id.takeIf { it != BePsHouseModel.NONE.id.id },
            name = model.name.takeIf { it != BePsHouseModel.NONE.name },
            nameIndex = model.name.takeIf { it != BePsHouseModel.NONE.name },
            timestamp = Instant.now()
        )
    }
}
