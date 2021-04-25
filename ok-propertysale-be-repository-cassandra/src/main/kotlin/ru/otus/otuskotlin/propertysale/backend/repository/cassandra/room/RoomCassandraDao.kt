package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface RoomByIdCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: RoomByIdCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<RoomByIdCassandraDto?>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${RoomByIdCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: RoomByIdCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    /**
     *  При удалении по ключу требуется указание [entityClass], при удалении по всей модели
     *  класс не требуется указывать, он берется из модели
     */
    @Delete(ifExists = true, entityClass = [RoomByIdCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}

@Dao
interface RoomByNameCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: RoomByNameCassandraDto): ListenableFuture<Unit>

    @Select(
        customWhereClause = "${RoomByNameCassandraDto.NAME_INDEX} LIKE :filter",
    )
    fun filterByNameAsync(filter: String): ListenableFuture<Collection<RoomByNameCassandraDto>>

//
//    @Query("SELECT ${RoomByNameCassandraDto.ID} FROM ${RoomByNameCassandraDto.ROOMS_NAME_TABLE_NAME}" +
//            "WHERE ${RoomByNameCassandraDto.NAME} LIKE :filter ORDER BY ${RoomByNameCassandraDto.TIMESTAMP} DESC")
//    fun filterByNameAsync(filter: String): ListenableFuture<Collection<String>>

    @Delete
    fun deleteAsync(dto: RoomByNameCassandraDto): ListenableFuture<Unit>
}
