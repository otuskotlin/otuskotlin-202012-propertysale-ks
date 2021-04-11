package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.application.*
import kotlinx.coroutines.runBlocking
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.service
import ru.otus.otuskotlin.propertysale.be.app.ktor.utils.toModel
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import java.time.Instant
import java.util.*

fun Application.rabbitMq(
    queueIn: String,
    exchangeOut: String,
    flatService: FlatService,
    houseService: HouseService,
    roomService: RoomService
) {
    rabbitConsumer {
        consume<PsMessage>(queueIn) { consumerTag, query ->
            println("Consumed message $query, consumer tag: $consumerTag")
            val ctx = BePsContext(
                responseId = UUID.randomUUID().toString(),
                timeStarted = Instant.now(),
            )
            try {
                ctx.status = BePsContextStatus.RUNNING
                runBlocking {
                    service(
                        context = ctx,
                        query = query,
                        flatService = flatService,
                        houseService = houseService,
                        roomService = roomService
                    )?.also {
                        publish(exchangeOut, "", null, it)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                ctx.status = BePsContextStatus.FAILING
                ctx.errors.add(e.toModel())
                runBlocking {
                    service(
                        context = ctx,
                        query = null,
                        flatService = flatService,
                        houseService = houseService,
                        roomService = roomService
                    )?.also {
                        publish(exchangeOut, "", null, it)
                    }
                }
            }
        }
    }
}
