package ru.otus.otuskotlin.propertysale.be.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.flatRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.houseRouting
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.roomRouting
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud

fun Application.module(testing: Boolean) {

    val flatCrud = FlatCrud()
    val houseCrud = HouseCrud()
    val roomCrud = RoomCrud()

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

    install(CallLogging)

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
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

        flatRouting(flatCrud)
        houseRouting(houseCrud)
        roomRouting(roomCrud)
    }
}
