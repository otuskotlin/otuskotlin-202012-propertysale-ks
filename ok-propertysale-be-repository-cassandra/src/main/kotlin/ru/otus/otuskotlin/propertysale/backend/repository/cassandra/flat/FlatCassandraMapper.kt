package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface FlatCassandraMapper {

    @DaoFactory
    fun flatByIdDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): FlatByIdCassandraDao

    @DaoFactory
    fun flatByNameDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): FlatByNameCassandraDao
}
