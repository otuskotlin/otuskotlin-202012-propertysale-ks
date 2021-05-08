package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface FlatByIdCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: FlatByIdCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<FlatByIdCassandraDto?>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${FlatByIdCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: FlatByIdCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    /**
     *  При удалении по ключу требуется указание [entityClass], при удалении по всей модели
     *  класс не требуется указывать, он берется из модели
     */
    @Delete(ifExists = true, entityClass = [FlatByIdCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}

@Dao
interface FlatByNameCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: FlatByNameCassandraDto): ListenableFuture<Unit>

    @Select(
        customWhereClause = "${FlatByNameCassandraDto.NAME_INDEX} LIKE :filter",
    )
    fun filterByNameAsync(filter: String): ListenableFuture<Collection<FlatByNameCassandraDto>>

//
//    @Query("SELECT ${FlatByNameCassandraDto.ID} FROM ${FlatByNameCassandraDto.FLATS_NAME_TABLE_NAME}" +
//            "WHERE ${FlatByNameCassandraDto.NAME} LIKE :filter ORDER BY ${FlatByNameCassandraDto.TIMESTAMP} DESC")
//    fun filterByNameAsync(filter: String): ListenableFuture<Collection<String>>

    @Delete
    fun deleteAsync(dto: FlatByNameCassandraDto): ListenableFuture<Unit>
}
