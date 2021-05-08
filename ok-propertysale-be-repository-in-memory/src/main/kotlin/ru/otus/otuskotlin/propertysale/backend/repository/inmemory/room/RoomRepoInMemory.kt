package ru.otus.otuskotlin.propertysale.backend.repository.inmemory.room

import org.cache2k.Cache
import org.cache2k.Cache2kBuilder
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoIndexException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoNotFoundException
import ru.otus.otuskotlin.propertysale.be.common.exceptions.PsRepoWrongIdException
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class RoomRepoInMemory @OptIn(ExperimentalTime::class) constructor(
    ttl: Duration,
    initObjects: Collection<BePsRoomModel> = emptyList()
): IRoomRepository {
    @OptIn(ExperimentalTime::class)
    private var cache: Cache<String, RoomInMemoryDto> = object : Cache2kBuilder<String, RoomInMemoryDto>() {}
        .expireAfterWrite(ttl.toLongMilliseconds(), TimeUnit.MILLISECONDS) // expire/refresh after 5 minutes
        .suppressExceptions(false)
        .build()
        .also { cache ->
            initObjects.forEach {
                cache.put(it.id.id, RoomInMemoryDto.of(it))
            }
        }

    override suspend fun read(context: BePsContext): BePsRoomModel {
        val id = context.requestRoomId
        if (id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(id.id)
        val model = cache.get(id.id)?.toModel()?: throw PsRepoNotFoundException(id.id)
        context.responseRoom = model
        return model
    }

    override suspend fun create(context: BePsContext): BePsRoomModel {
        val dto = RoomInMemoryDto.of(context.requestRoom, UUID.randomUUID().toString())
        val model = save(dto).toModel()
        context.responseRoom = model
        return model
    }

    override suspend fun update(context: BePsContext): BePsRoomModel {
        if (context.requestRoom.id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(context.requestRoom.id.id)
        val model = save(RoomInMemoryDto.of(context.requestRoom)).toModel()
        context.responseRoom = model
        return model
    }

    override suspend fun delete(context: BePsContext): BePsRoomModel {
        val id = context.requestRoomId
        if (id == BePsRoomIdModel.NONE) throw PsRepoWrongIdException(id.id)
        val model = cache.peekAndRemove(id.id)?.toModel()?: throw PsRepoNotFoundException(id.id)
        context.responseRoom = model
        return model
    }

    override suspend fun list(context: BePsContext): Collection<BePsRoomModel> {
        val textFilter = context.roomFilter.text
        if (textFilter.length < 3) throw PsRepoIndexException(textFilter)
        val records = cache.asMap().filterValues {
            it.name?.contains(textFilter, true)?:false || if (context.roomFilter.includeDescription) {
                it.description?.contains(textFilter, true) ?: false
            } else false
        }.values
        if (records.count() <= context.roomFilter.offset)
            throw PsRepoIndexException(textFilter)
        val list = records.toList()
            .subList(
                context.roomFilter.offset,
                if (records.count() >= context.roomFilter.offset + context.roomFilter.count)
                    context.roomFilter.offset + context.roomFilter.count
                else records.count()
            ).map { it.toModel() }
        context.responseRooms = list.toMutableList()
        context.pageCount = list.count().takeIf { it != 0 }
            ?.let { (records.count().toDouble() / it + 0.5).toInt() }
            ?: Int.MIN_VALUE
        return list
    }

    private suspend fun save(dto: RoomInMemoryDto): RoomInMemoryDto {
        cache.put(dto.id, dto)
        return cache.get(dto.id)
    }
}
