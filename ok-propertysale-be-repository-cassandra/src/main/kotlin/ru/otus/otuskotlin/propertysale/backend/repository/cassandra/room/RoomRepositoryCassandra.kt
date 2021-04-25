package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room

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
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository
import java.time.Duration
import java.util.*

class RoomRepositoryCassandra(
    override val keyspaceName: String,
    override val hosts: String = "",
    override val port: Int = 9042,
    override val user: String = "cassandra",
    override val pass: String = "cassandra",
    override val replicationFactor: Int = 1,
    override val testing: Boolean = false,
    private val timeout: Duration = Duration.ofSeconds(30),
    initObjects: Collection<BePsRoomModel> = emptyList(),
) : IRoomRepository, PsRepositoryCassandra(keyspaceName, hosts, port, user, pass, replicationFactor) {

    private val mapper by lazy {
        RoomCassandraMapperBuilder(session).build()
    }

    /**
     *  DAO для операций по id.
     */
    private val daoById by lazy {
        mapper.roomByIdDao(keyspaceName, RoomByIdCassandraDto.ROOMS_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(RoomByIdCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    /**
     *  DAO для операций с названиями.
     */
    private val daoByName by lazy {
        mapper.roomByNameDao(keyspaceName, RoomByNameCassandraDto.ROOMS_NAME_TABLE_NAME).apply {
            runBlocking {
                initObjects.map { model ->
                    withTimeout(timeout.toMillis()) {
                        createAsync(RoomByNameCassandraDto.of(model)).await()
                    }
                }
            }
        }
    }

    override suspend fun read(context: BePsContext): BePsRoomModel {
        val id = context.requestRoomId
        if (id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseRoom = model
            model
        }
    }

    /**
     * Запись происходит во все таблицы.
     */
    override suspend fun create(context: BePsContext): BePsRoomModel {
        val id = UUID.randomUUID().toString()
        val dtoById = RoomByIdCassandraDto.of(context.requestRoom, id)
        val dtoByName = RoomByNameCassandraDto.of(context.requestRoom, id)
        return withTimeout(timeout.toMillis()) {
            daoById.createAsync(dtoById).await()
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id).await()?.toModel() ?: throw PsRepoNotFoundException(id)
            context.responseRoom = model
            model
        }
    }

    /**
     *  Использование Optimistic Lock для примера, в данном случае update аналогичен create.
     */
    override suspend fun update(context: BePsContext): BePsRoomModel {
        val id = context.requestRoom.id
        if (id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val lockKey = daoById.readAsync(id.id).await()?.lockVersion ?: throw PsRepoNotFoundException(id.id)
            val dtoById = RoomByIdCassandraDto.of(context.requestRoom)
            val dtoByName = RoomByNameCassandraDto.of(context.requestRoom)
            val isUpdated = daoById.updateAsync(dtoById, lockKey).await()
            if (!isUpdated) throw PsRepoModifyException(id.id)
            daoByName.createAsync(dtoByName).await()
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            context.responseRoom = model
            model
        }
    }

    /**
     * Удаление записи из всех таблиц.
     */
    override suspend fun delete(context: BePsContext): BePsRoomModel {
        val id = context.requestRoomId
        if (id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(id.id)
        return withTimeout(timeout.toMillis()) {
            val model = daoById.readAsync(id.id).await()?.toModel() ?: throw PsRepoNotFoundException(id.id)
            daoById.deleteAsync(id.id).await()
            daoByName.deleteAsync(RoomByNameCassandraDto.of(model)).await()
            context.responseRoom = model
            model
        }
    }

    override suspend fun list(context: BePsContext): Collection<BePsRoomModel> {
        val filter = context.roomFilter
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
            context.responseRooms = list.toMutableList()
            context.pageCount = list.count().takeIf { it != 0 }
                ?.let { (recordsCount.toDouble() / it + 0.5).toInt() }
                ?: Int.MIN_VALUE
            list
        }

    }

    override fun CqlSession.createTables() {
        execute(
            SchemaBuilder.createTable(RoomByNameCassandraDto.ROOMS_NAME_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(RoomByNameCassandraDto.NAME, DataTypes.TEXT)
                .withClusteringColumn(RoomByNameCassandraDto.TIMESTAMP, DataTypes.TIMESTAMP)
                .withClusteringColumn(RoomByNameCassandraDto.ID, DataTypes.TEXT)
                .withClusteringColumn(RoomByNameCassandraDto.NAME_INDEX, DataTypes.TEXT)
                .withClusteringOrder(RoomByNameCassandraDto.TIMESTAMP, ClusteringOrder.DESC)
                .withClusteringOrder(RoomByNameCassandraDto.ID, ClusteringOrder.ASC)
                .withClusteringOrder(RoomByNameCassandraDto.NAME_INDEX, ClusteringOrder.ASC)
                .build()
        )
        execute(
            SchemaBuilder.createTable(RoomByIdCassandraDto.ROOMS_TABLE_NAME)
                .ifNotExists()
                .withPartitionKey(RoomByIdCassandraDto.ID, DataTypes.TEXT)
                .withColumn(RoomByIdCassandraDto.NAME, DataTypes.TEXT)
                .withColumn(RoomByIdCassandraDto.DESCRIPTION, DataTypes.TEXT)
                .withColumn(RoomByIdCassandraDto.LENGTH, DataTypes.DOUBLE)
                .withColumn(RoomByIdCassandraDto.WIDTH, DataTypes.DOUBLE)
                .withColumn(
                    RoomByIdCassandraDto.ACTIONS,
                    DataTypes.setOf(SchemaBuilder.udt(ActionCassandraDto.TYPE_NAME, true))
                )
                .withColumn(RoomByIdCassandraDto.LOCK_VERSION, DataTypes.TEXT)
                .build()
        )
    }

    override fun CqlSession.createIndexes() {
        execute(
            SchemaBuilder.createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(RoomByNameCassandraDto.ROOMS_NAME_TABLE_NAME)
                .andColumn(RoomByNameCassandraDto.NAME_INDEX)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to "en"))
                .build()
        )
    }

    override fun init() = apply {
        val dao1 = daoById
        val dao2 = daoByName
    }
}
