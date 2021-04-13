package ru.otus.otuskotlin.propertysale.be.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.flatRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.houseRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.mpWebsocket
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.rabbitMq
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.roomRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(InternalSerializationApi::class)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val flatService = FlatService(FlatCrud())
    val houseService = HouseService(HouseCrud())
    val roomService = RoomService(RoomCrud())

    val queueIn by lazy { environment.config.property("propertysale.rabbitmq.queueIn").getString() }
    val exchangeIn by lazy { environment.config.property("propertysale.rabbitmq.exchangeIn").getString() }
    val exchangeOut by lazy { environment.config.property("propertysale.rabbitmq.exchangeOut").getString() }
    val rabbitMqEndpoint by lazy { environment.config.property("propertysale.rabbitmq.endpoint").getString() }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(WebSockets)

    install(CallLogging)

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
        )
    }

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

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        flatRouting(flatService)
        houseRouting(houseService)
        roomRouting(roomService)

        mpWebsocket(flatService, houseService, roomService)

        rabbitMq(
            queueIn = queueIn,
            exchangeOut = exchangeOut,
            flatService = flatService,
            houseService = houseService,
            roomService = roomService
        )
    }
}
