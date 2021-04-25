package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.producers

import com.datastax.oss.driver.api.core.cql.Statement
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture

class FutureOfBooleanProducer : MapperResultProducer {
    override fun canProduce(resultType: GenericType<*>) =
        resultType == PRODUCED_TYPE

    override fun execute(
        statement: Statement<*>,
        context: MapperContext,
        entityHelper: EntityHelper<*>?
    ): ListenableFuture<*> {
        val result = SettableFuture.create<Any>()
        val session = context.session
        session.executeAsync(statement)
            .whenComplete{
                    resultSet, error ->
                if (error != null) {
                    result.setException(error)
                } else {
                    result.set(resultSet.one()?.getBoolean(0))
                }
            }
        return result
    }

    override fun wrapError(e: Exception): ListenableFuture<*> {
        return Futures.immediateFailedFuture<Any>(e)
    }

    companion object {
        private val PRODUCED_TYPE: GenericType<ListenableFuture<Boolean>> = object : GenericType<ListenableFuture<Boolean>>() {}
    }
}
