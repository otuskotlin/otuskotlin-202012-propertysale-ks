package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house

import com.datastax.oss.driver.api.mapper.annotations.*
import com.google.common.util.concurrent.ListenableFuture
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house.HouseByIdCassandraDto

@Dao
interface HouseByIdCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: HouseByIdCassandraDto): ListenableFuture<Unit>

    @Select
    fun readAsync(id: String): ListenableFuture<HouseByIdCassandraDto?>

    /**
     *  В данном случае условие в Update избыточно, так как обновляется вся модель.
     *  Может быть нужно при обновлении отдельных полей
     */
    @Update(customIfClause = "${HouseByIdCassandraDto.LOCK_VERSION} = :lock_key")
    @StatementAttributes(consistencyLevel = "QUORUM")
    fun updateAsync(dto: HouseByIdCassandraDto, @CqlName("lock_key") lockKey: String): ListenableFuture<Boolean>

    /**
     *  При удалении по ключу требуется указание [entityClass], при удалении по всей модели
     *  класс не требуется указывать, он берется из модели
     */
    @Delete(ifExists = true, entityClass = [HouseByIdCassandraDto::class])
    fun deleteAsync(id: String): ListenableFuture<Boolean>
}

@Dao
interface HouseByNameCassandraDao {

    @Insert
    @StatementAttributes(consistencyLevel = "ONE")
    fun createAsync(dto: HouseByNameCassandraDto): ListenableFuture<Unit>

    @Select(
        customWhereClause = "${HouseByNameCassandraDto.NAME_INDEX} LIKE :filter",
    )
    fun filterByNameAsync(filter: String): ListenableFuture<Collection<HouseByNameCassandraDto>>

//
//    @Query("SELECT ${HouseByNameCassandraDto.ID} FROM ${HouseByNameCassandraDto.HOUSES_NAME_TABLE_NAME}" +
//            "WHERE ${HouseByNameCassandraDto.NAME} LIKE :filter ORDER BY ${HouseByNameCassandraDto.TIMESTAMP} DESC")
//    fun filterByNameAsync(filter: String): ListenableFuture<Collection<String>>

    @Delete
    fun deleteAsync(dto: HouseByNameCassandraDto): ListenableFuture<Unit>
}
