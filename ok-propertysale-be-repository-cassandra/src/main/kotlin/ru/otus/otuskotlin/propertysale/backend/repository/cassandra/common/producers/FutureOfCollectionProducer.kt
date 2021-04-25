package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.producers

import com.datastax.oss.driver.api.core.cql.Statement
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import java.lang.reflect.ParameterizedType

class FutureOfCollectionProducer: MapperResultProducer {
    override fun canProduce(resultType: GenericType<*>): Boolean {
        if (resultType.rawType == ListenableFuture::class.java) {
            val type = resultType.type
            if (type is ParameterizedType) {
                val arguments = type.actualTypeArguments
                if (arguments.size == 1) {
                    val argument = arguments[0]
                    return argument is ParameterizedType
                            && argument.rawType == Collection::class.java
                }
            }
        }
        return false
    }

    override fun execute(
        statement: Statement<*>,
        context: MapperContext,
        entityHelper: EntityHelper<*>?
    ): ListenableFuture<Collection<*>> {
        assert(entityHelper != null)
        val result = SettableFuture.create<Collection<*>>()
        val session = context.session
        session.executeAsync(statement)
            .whenComplete{
                    resultSet, error ->
                if (error != null) {
                    result.setException(error)
                } else {
                    val rows: MutableList<Any> = mutableListOf()
                    while (true) {
                        val row = resultSet.one() ?: break
                        rows.add(entityHelper!!.get(row))
                    }
                    result.set(rows.toList())
                }
            }
        println(result.toString())
        return result
    }

    override fun wrapError(e: Exception): ListenableFuture<Collection<*>> {
        return Futures.immediateFailedFuture(e)
    }
}
