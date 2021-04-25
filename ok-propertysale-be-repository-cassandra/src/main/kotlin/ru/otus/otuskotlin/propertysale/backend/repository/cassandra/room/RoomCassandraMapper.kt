package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface RoomCassandraMapper {

    @DaoFactory
    fun roomByIdDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): RoomByIdCassandraDao

    @DaoFactory
    fun roomByNameDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): RoomByNameCassandraDao
}
