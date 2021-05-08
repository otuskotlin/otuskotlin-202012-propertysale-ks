package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.PsRepositoryCassandra
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.dto.ActionCassandraDto
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoIndexException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoModifyException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoNotFoundException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoWrongIdException
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import java.time.Duration
import java.util.*

class HouseRepositoryCassandra(
    override val keyspaceName: String,
    override val hosts: String = "",
    override val port: Int = 9042,
    override val user: String = "cassandra",
    override val pass: String = "cassandra",
    override val replicationFactor: Int = 1,
    override val testing: Boolean = false,
    private val timeout: Duration = Duration.ofSeconds(30),
    initObjects: Collection<BePsHouseModel> = emptyList(),
) : IHouseRepository, PsRepositoryCassandra(keyspaceName, hosts, port, user, pass, replicationFactor) {

    private val mapper by lazy {
        HouseCassandraMapperBuilder(session).build()
    }

    /**
     *  DAO для операций по id.
     */
    private val daoById by lazy {
        mapper.houseByIdDao(keyspaceName, HouseByIdCassandraDto.HOUSES_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(HouseByIdCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    /**
     *  DAO для операций с названиями.
     */
    private val daoByName by lazy {
        mapper.houseByNameDao(keyspaceName, HouseByNameCassandraDto.HOUSES_NAME_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(HouseByNameCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: BePsContext): BePsHouseModel {
        val id = context.requestHouseId
        if (id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseHouse = model
            model
        }
    }

    /**
     * Запись происходит во все таблицы.
     */
    override suspend fun create(context: BePsContext): BePsHouseModel {
        val id = UUID.randomUUID().toString()
        val dtoById = HouseByIdCassandraDto.of(context.requestHouse, id)
        val dtoByName = HouseByNameCassandraDto.of(context.requestHouse, id)
        return withTimeout(timeout.toMillis()) {
            daoById.createAsync(dtoById).await()
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id).await()?.toModel() ?: throw PsRepoNotFoundException(id)
            context.responseHouse = model
            model
        }
    }

    /**
     *  Использование Optimistic Lock для примера, в данном случае update аналогичен create.
     */
    override suspend fun update(context: BePsContext): BePsHouseModel {
        val id = context.requestHouse.id
        if (id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val lockKey = daoById.readAsync(id.id).await()?.lockVersion ?: throw PsRepoNotFoundException(id.id)
            val dtoById = HouseByIdCassandraDto.of(context.requestHouse)
            val dtoByName = HouseByNameCassandraDto.of(context.requestHouse)
            val isUpdated = daoById.updateAsync(dtoById, lockKey).await()
            if (!isUpdated) throw PsRepoModifyException(id.id)
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseHouse = model
            model
        }
    }

    /**
     * Удаление записи из всех таблиц.
     */
    override suspend fun delete(context: BePsContext): BePsHouseModel {
        val id = context.requestHouseId
        if (id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            daoById.deleteAsync(id.id).await()
            daoByName.deleteAsync(HouseByNameCassandraDto.of(model)).await()
            context.responseHouse = model
            model
        }
    }

    override suspend fun list(context: BePsContext): Collection<BePsHouseModel> {
        val filter = context.houseFilter
        var lastIndex = filter.offset + filter.count
        if (filter.text.length < 3) throw PsRepoIndexException(filter.text)
        return withTimeout(timeout.toMillis()) {
            val records = daoByName.filterByNameAsync("%${filter.text}%").await().toList()
                .sortedByDescending { it.timestamp }
            val recordsCount = records.count()
            if (recordsCount < lastIndex) lastIndex = recordsCount
            val list = flow {
                for (pos in filter.offset until lastIndex) {
                    records[pos].id?.let { id ->
                        emit(daoById.readAsync(id).await()?.toModel() ?: throw PsRepoNotFoundException(id))
                    }
                }
            }.toList()
            context.responseHouses = list.toMutableList()
            context.pageCount = list.count().takeIf { it != 0 }
                ?.let { (recordsCount.toDouble() / it + 0.5).toInt() }
                ?: Int.MIN_VALUE
            list
        }

    }

    override fun CqlSession.createTables() {
        execute(
            SchemaBuilder.createTable(HouseByNameCassandraDto.HOUSES_NAME_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(HouseByNameCassandraDto.NAME, DataTypes.TEXT)
                .withClusteringColumn(HouseByNameCassandraDto.TIMESTAMP, DataTypes.TIMESTAMP)
                .withClusteringColumn(HouseByNameCassandraDto.ID, DataTypes.TEXT)
                .withClusteringColumn(HouseByNameCassandraDto.NAME_INDEX, DataTypes.TEXT)
                .withClusteringOrder(HouseByNameCassandraDto.TIMESTAMP, ClusteringOrder.DESC)
                .withClusteringOrder(HouseByNameCassandraDto.ID, ClusteringOrder.ASC)
                .withClusteringOrder(HouseByNameCassandraDto.NAME_INDEX, ClusteringOrder.ASC)
                .build()
        )
        execute(
            SchemaBuilder.createTable(HouseByIdCassandraDto.HOUSES_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(HouseByIdCassandraDto.ID, DataTypes.TEXT)
                .withColumn(HouseByIdCassandraDto.NAME, DataTypes.TEXT)
                .withColumn(HouseByIdCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(HouseByIdCassandraDto.AREA, DataTypes.DOUBLE)
                .withColumn(
                    HouseByIdCassandraDto.ACTIONS,
                    DataTypes.setOf(SchemaBuilder.udt(ActionCassandraDto.TYPE_NAME, true))
                )
                .withColumn(HouseByIdCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .build()
        )
    }

    override fun CqlSession.createIndexes() {
        execute(
            SchemaBuilder.createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(HouseByNameCassandraDto.HOUSES_NAME_TABLE_NAME)
                .andColumn(HouseByNameCassandraDto.NAME_INDEX)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to "en"))
                .build()
        )
    }

    override fun init() = apply {
        val dao1 = daoById
        val dao2 = daoByName
    }
}
