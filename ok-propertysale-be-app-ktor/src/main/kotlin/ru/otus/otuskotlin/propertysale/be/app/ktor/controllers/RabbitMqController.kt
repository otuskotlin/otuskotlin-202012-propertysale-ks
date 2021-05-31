package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import io.ktor.application.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
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

@OptIn(InternalSerializationApi::class)
fun Application.rabbitMqEndpoints(
    rabbitMqEndpoint: String,
    flatService: FlatService,
    houseService: HouseService,
    roomService: RoomService
) {
    val queueIn by lazy { environment.config.property("propertysale.rabbitmq.queueIn").getString() }
    val exchangeIn by lazy { environment.config.property("propertysale.rabbitmq.exchangeIn").getString() }
    val exchangeOut by lazy { environment.config.property("propertysale.rabbitmq.exchangeOut").getString() }

    install(RabbitMQ) {
        uri = rabbitMqEndpoint
        connectionName = "Connection name"

        //serialize and deserialize functions are required
        serialize {
            when (it) {
                is PsMessage -> jsonConfig.encodeToString(PsMessage.serializer(), it).toByteArray()
                else -> jsonConfig.encodeToString(Any::class.serializer(), it).toByteArray()
            }
        }
        deserialize { bytes, type ->
            val jsonString = String(bytes, Charsets.UTF_8)
            jsonConfig.decodeFromString(type.serializer(), jsonString)
        }

        //example initialization logic
        initialize {
            exchangeDeclare(exchangeIn, "fanout", true)
            exchangeDeclare(exchangeOut, "fanout", true)
            queueDeclare(queueIn, true, false, false, emptyMap())
            queueBind(
                queueIn,
                exchangeIn,
                "*"
            )
        }
    }
    rabbitMq(
        queueIn = queueIn,
        exchangeOut = exchangeOut,
        flatService = flatService,
        houseService = houseService,
        roomService = roomService,
    )
}

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
