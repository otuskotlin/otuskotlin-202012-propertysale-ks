package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface HouseCassandraMapper {

    @DaoFactory
    fun houseByIdDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): HouseByIdCassandraDao

    @DaoFactory
    fun houseByNameDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): HouseByNameCassandraDao
}
