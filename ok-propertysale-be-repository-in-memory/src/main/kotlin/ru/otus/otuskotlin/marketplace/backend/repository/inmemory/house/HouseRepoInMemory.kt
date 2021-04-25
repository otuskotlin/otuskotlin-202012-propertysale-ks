package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.house

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoIndexException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoNotFoundException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoWrongIdException
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.house.BePsHouseModel
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class HouseRepoInMemory @OptIn(ExperimentalTime::class) constructor(
    ttl: Duration,
    initObjects: Collection<BePsHouseModel> = emptyList()
): IHouseRepository {
    @OptIn(ExperimentalTime::class)
    private var cache: Cache<String, HouseInMemoryDto> = object : Cache2kBuilder<String, HouseInMemoryDto>() {}
        .expireAfterWrite(ttl.toLongMilliseconds(), TimeUnit.MILLISECONDS) // expire/refresh after 5 minutes
        .suppressExceptions(false)
        .build()
        .also { cache ->
            initObjects.forEach {
                cache.put(it.id.id, HouseInMemoryDto.of(it))
            }
        }

    override suspend fun read(context: BePsContext): BePsHouseModel {
        val id = context.requestHouseId
        if (id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(id.id)
        val model = cache.get(id.id)?.toModel()?: throw PsRepoNotFoundException(id.id)
        context.responseHouse = model
        return model
    }

    override suspend fun create(context: BePsContext): BePsHouseModel {
        val dto = HouseInMemoryDto.of(context.requestHouse, UUID.randomUUID().toString())
        val model = save(dto).toModel()
        context.responseHouse = model
        return model
    }

    override suspend fun update(context: BePsContext): BePsHouseModel {
        if (context.requestHouse.id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(context.requestHouse.id.id)
        val model = save(HouseInMemoryDto.of(context.requestHouse)).toModel()
        context.responseHouse = model
        return model
    }

    override suspend fun delete(context: BePsContext): BePsHouseModel {
        val id = context.requestHouseId
        if (id == BePsHouseIdModel.NONE) throw PsRepoWrongIdException(id.id)
        val model = cache.peekAndRemove(id.id)?.toModel()?: throw PsRepoNotFoundException(id.id)
        context.responseHouse = model
        return model
    }

    override suspend fun list(context: BePsContext): Collection<BePsHouseModel> {
        val textFilter = context.houseFilter.text
        if (textFilter.length < 3) throw PsRepoIndexException(textFilter)
        val records = cache.asMap().filterValues {
            it.name?.contains(textFilter, true)?:false || if (context.houseFilter.includeDescription) {
                it.description?.contains(textFilter, true) ?: false
            } else false
        }.values
        if (records.count() <= context.houseFilter.offset)
            throw PsRepoIndexException(textFilter)
        val list = records.toList()
            .subList(
                context.houseFilter.offset,
                if (records.count() >= context.houseFilter.offset + context.houseFilter.count)
                    context.houseFilter.offset + context.houseFilter.count
                else records.count()
            ).map { it.toModel() }
        context.responseHouses = list.toMutableList()
        context.pageCount = list.count().takeIf { it != 0 }
            ?.let { (records.count().toDouble() / it + 0.5).toInt() }
            ?: Int.MIN_VALUE
        return list
    }

    private suspend fun save(dto: HouseInMemoryDto): HouseInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }
}
