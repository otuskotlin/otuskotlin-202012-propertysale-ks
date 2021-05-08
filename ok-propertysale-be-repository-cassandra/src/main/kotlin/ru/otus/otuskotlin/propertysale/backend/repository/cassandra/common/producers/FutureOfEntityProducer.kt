package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.producers

import com.datastax.oss.driver.api.core.cql.Statement
import com.datastax.oss.driver.api.core.type.reflect.GenericType
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture

class FutureOfEntityProducer(
    private vararg val entityClasses: Class<*>
): MapperResultProducer {
    override fun canProduce(resultType: GenericType<*>): Boolean {
        return resultType.isTargetEntity(entityClasses)
    }

    override fun execute(
        statement: Statement<*>,
        context: MapperContext,
        entityHelper: EntityHelper<*>?
    ): ListenableFuture<*> {
        assert(entityHelper != null)
        val result = SettableFuture.create<Any>()
        val session = context.session
        session.executeAsync(statement)
            .whenComplete{
                resultSet, error ->
                    if (error != null) {
                        result.setException(error)
                    } else {
                        val row = resultSet.one()
                        result.set(if (row == null) null else entityHelper!!.get(row))
                    }
            }
        return result
    }

    override fun wrapError(e: Exception): ListenableFuture<*> {
       return Futures.immediateFailedFuture<Any>(e)
    }

    private fun GenericType<*>.isTargetEntity(entityClasses: Array<out Class<*>>): Boolean {
        val typeName = this.type.typeName
        val lfName = ListenableFuture::class.java.name
        if (this.rawType != ListenableFuture::class.java)
            return false
        if (entityClasses.isNullOrEmpty())
            return true
        val types: MutableList<String> = mutableListOf()
        entityClasses.forEach {
            types.add(lfName + "<${it.name}>")
        }
        return types.contains(typeName)
    }
}

