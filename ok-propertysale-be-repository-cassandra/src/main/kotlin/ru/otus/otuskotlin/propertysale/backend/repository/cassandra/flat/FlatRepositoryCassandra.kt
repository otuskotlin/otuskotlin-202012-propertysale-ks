package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat

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
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.flat.BePsFlatModel
import ru.otus.otuskotlin.propertysale.be.common.repositories.IFlatRepository
import java.time.Duration
import java.util.*

class FlatRepositoryCassandra(
    override val keyspaceName: String,
    override val hosts: String = "",
    override val port: Int = 9042,
    override val user: String = "cassandra",
    override val pass: String = "cassandra",
    override val replicationFactor: Int = 1,
    override val testing: Boolean = false,
    private val timeout: Duration = Duration.ofSeconds(30),
    initObjects: Collection<BePsFlatModel> = emptyList(),
) : IFlatRepository, PsRepositoryCassandra(keyspaceName, hosts, port, user, pass, replicationFactor) {

    private val mapper by lazy {
        FlatCassandraMapperBuilder(session).build()
    }

    /**
     *  DAO для операций по id.
     */
    private val daoById by lazy {
        mapper.flatByIdDao(keyspaceName, FlatByIdCassandraDto.FLATS_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(FlatByIdCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    /**
     *  DAO для операций с названиями.
     */
    private val daoByName by lazy {
        mapper.flatByNameDao(keyspaceName, FlatByNameCassandraDto.FLATS_NAME_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(FlatByNameCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: BePsContext): BePsFlatModel {
        val id = context.requestFlatId
        if (id == BePsFlatIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseFlat = model
            model
        }
    }

    /**
     * Запись происходит во все таблицы.
     */
    override suspend fun create(context: BePsContext): BePsFlatModel {
        val id = UUID.randomUUID().toString()
        val dtoById = FlatByIdCassandraDto.of(context.requestFlat, id)
        val dtoByName = FlatByNameCassandraDto.of(context.requestFlat, id)
        return withTimeout(timeout.toMillis()) {
            daoById.createAsync(dtoById).await()
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id).await()?.toModel() ?: throw PsRepoNotFoundException(id)
            context.responseFlat = model
            model
        }
    }

    /**
     *  Использование Optimistic Lock для примера, в данном случае update аналогичен create.
     */
    override suspend fun update(context: BePsContext): BePsFlatModel {
        val id = context.requestFlat.id
        if (id == BePsFlatIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val lockKey = daoById.readAsync(id.id).await()?.lockVersion ?: throw PsRepoNotFoundException(id.id)
            val dtoById = FlatByIdCassandraDto.of(context.requestFlat)
            val dtoByName = FlatByNameCassandraDto.of(context.requestFlat)
            val isUpdated = daoById.updateAsync(dtoById, lockKey).await()
            if (!isUpdated) throw PsRepoModifyException(id.id)
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseFlat = model
            model
        }
    }

    /**
     * Удаление записи из всех таблиц.
     */
    override suspend fun delete(context: BePsContext): BePsFlatModel {
        val id = context.requestFlatId
        if (id == BePsFlatIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            daoById.deleteAsync(id.id).await()
            daoByName.deleteAsync(FlatByNameCassandraDto.of(model)).await()
            context.responseFlat = model
            model
        }
    }

    override suspend fun list(context: BePsContext): Collection<BePsFlatModel> {
        val filter = context.flatFilter
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
            context.responseFlats = list.toMutableList()
            context.pageCount = list.count().takeIf { it != 0 }
                ?.let { (recordsCount.toDouble() / it + 0.5).toInt() }
                ?: Int.MIN_VALUE
            list
        }

    }

    override fun CqlSession.createTables() {
        execute(
            SchemaBuilder.createTable(FlatByNameCassandraDto.FLATS_NAME_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(FlatByNameCassandraDto.NAME, DataTypes.TEXT)
                .withClusteringColumn(FlatByNameCassandraDto.TIMESTAMP, DataTypes.TIMESTAMP)
                .withClusteringColumn(FlatByNameCassandraDto.ID, DataTypes.TEXT)
                .withClusteringColumn(FlatByNameCassandraDto.NAME_INDEX, DataTypes.TEXT)
                .withClusteringOrder(FlatByNameCassandraDto.TIMESTAMP, ClusteringOrder.DESC)
                .withClusteringOrder(FlatByNameCassandraDto.ID, ClusteringOrder.ASC)
                .withClusteringOrder(FlatByNameCassandraDto.NAME_INDEX, ClusteringOrder.ASC)
                .build()
        )
        execute(
            SchemaBuilder.createTable(FlatByIdCassandraDto.FLATS_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(FlatByIdCassandraDto.ID, DataTypes.TEXT)
                .withColumn(FlatByIdCassandraDto.NAME, DataTypes.TEXT)
                .withColumn(FlatByIdCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(FlatByIdCassandraDto.FLOOR, DataTypes.INT)
                .withColumn(FlatByIdCassandraDto.NUMBER_OF_ROOMS, DataTypes.INT)
                .withColumn(
                    FlatByIdCassandraDto.ACTIONS,
                    DataTypes.setOf(SchemaBuilder.udt(ActionCassandraDto.TYPE_NAME, true))
                )
                .withColumn(FlatByIdCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .build()
        )
    }

    override fun CqlSession.createIndexes() {
        execute(
            SchemaBuilder.createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(FlatByNameCassandraDto.FLATS_NAME_TABLE_NAME)
                .andColumn(FlatByNameCassandraDto.NAME_INDEX)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to "en"))
                .build()
        )
    }

    override fun init() = apply {
        val dao1 = daoById
        val dao2 = daoByName
    }
}
