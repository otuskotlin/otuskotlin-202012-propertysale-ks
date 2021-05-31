package ru.otus.otuskotlin.propertysale.be.app.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.flat.FlatRepositoryCassandra
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.house.HouseRepositoryCassandra
import ru.otus.otuskotlin.propertysale.backend.repository.cassandra.room.RoomRepositoryCassandra
import ru.otus.otuskotlin.propertysale.backend.repository.inmemory.flat.FlatRepoInMemory
import ru.otus.otuskotlin.propertysale.backend.repository.inmemory.house.HouseRepoInMemory
import ru.otus.otuskotlin.propertysale.backend.repository.inmemory.room.RoomRepoInMemory
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.AuthConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.CassandraConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.featureAuth
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.featureRest
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.flatRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.houseRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.rabbitMqEndpoints
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.roomRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.websocketEndpoints
import ru.otus.otuskotlin.propertysale.be.app.ktor.exceptions.WrongConfigException
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.be.common.repositories.IFlatRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IHouseRepository
import ru.otus.otuskotlin.propertysale.be.common.repositories.IRoomRepository
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(ExperimentalTime::class)
@Suppress("unused") // Referenced in application.conf
fun Application.module(
    testing: Boolean = false,
    authOff: Boolean = false,
    testFlatRepo: IFlatRepository? = null,
    testHouseRepo: IHouseRepository? = null,
    testRoomRepo: IRoomRepository? = null,
) {
    val authConfig by lazy { AuthConfig(environment, authOff) }
    val cassandraConfig by lazy { CassandraConfig(environment) }

    featureAuth(authConfig)
    featureRest()

    val repoProdName by lazy {
        environment.config.propertyOrNull("propertysale.repository.prod")
            ?.getString()
            ?.trim()
            ?.toLowerCase()
            ?: "inmemory"
    }

    val flatRepoProd = when (repoProdName) {
        "cassandra" -> FlatRepositoryCassandra(
            keyspaceName = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass,
        )
        "inmemory" -> FlatRepoInMemory()
        else -> throw WrongConfigException("Flat repository is not set")
    }
    val houseRepoProd = when (repoProdName) {
        "cassandra" -> HouseRepositoryCassandra(
            keyspaceName = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass,
        )
        "inmemory" -> HouseRepoInMemory()
        else -> throw WrongConfigException("House repository is not set")
    }
    val roomRepoProd = when (repoProdName) {
        "cassandra" -> RoomRepositoryCassandra(
            keyspaceName = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass,
        )
        "inmemory" -> RoomRepoInMemory()
        else -> throw WrongConfigException("Room repository is not set")
    }

    val flatRepoTest = testFlatRepo ?: FlatRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val houseRepoTest = testHouseRepo ?: HouseRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val roomRepoTest = testRoomRepo ?: RoomRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val flatCrud = FlatCrud(
        flatRepoTest = flatRepoTest,
        flatRepoProd = flatRepoProd,
    )
    val houseCrud = HouseCrud(
        houseRepoTest = houseRepoTest,
        houseRepoProd = houseRepoProd,
    )
    val roomCrud = RoomCrud(
        roomRepoTest = roomRepoTest,
        roomRepoProd = roomRepoProd,
    )

    val flatService = FlatService(flatCrud)
    val houseService = HouseService(houseCrud)
    val roomService = RoomService(roomCrud)

    // Подключаем Websocket
    websocketEndpoints(
        flatService = flatService,
        houseService = houseService,
        roomService = roomService,
    )

    // Подключаем RabbitMQ
    val rabbitMqEndpoint = environment.config.propertyOrNull("propertysale.rabbitmq.endpoint")?.getString()
    if (rabbitMqEndpoint != null) {
        rabbitMqEndpoints(
            rabbitMqEndpoint = rabbitMqEndpoint,
            flatService = flatService,
            houseService = houseService,
            roomService = roomService,
        )
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
    }
}
