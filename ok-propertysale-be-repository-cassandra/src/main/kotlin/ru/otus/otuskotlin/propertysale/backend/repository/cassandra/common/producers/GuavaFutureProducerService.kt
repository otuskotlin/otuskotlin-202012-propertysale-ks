package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.producers

import com.datastax.oss.driver.api.mapper.result.MapperResultProducer
import com.datastax.oss.driver.api.mapper.result.MapperResultProducerService

/**
 * Важен порядок в списке продюсеров, поиск подходящего продюсера в маппере производится последовательно.
 */
class GuavaFutureProducerService : MapperResultProducerService {
    override fun getProducers(): Iterable<MapperResultProducer> =
        listOf(
            FutureOfCollectionProducer(),
            FutureOfUnitProducer(),
            FutureOfBooleanProducer(),
            FutureOfEntityProducer(),
        )
}
