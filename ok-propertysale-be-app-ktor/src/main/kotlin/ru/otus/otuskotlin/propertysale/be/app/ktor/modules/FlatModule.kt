package ru.otus.otuskotlin.propertysale.be.app.ktor.modules

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.FlatController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatUpdate

fun Application.flatModule(testing: Boolean = false) {

    val flatController = FlatController()

    routing {
        route("/flat") {
            post("/read") {
                try {
                    val query = call.receive<PsRequestFlatRead>()
                    call.respond(flatController.read(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseFlatRead(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/create") {
                try {
                    val query = call.receive<PsRequestFlatCreate>()
                    call.respond(flatController.create(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseFlatCreate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/update") {
                try {
                    val query = call.receive<PsRequestFlatUpdate>()
                    call.respond(flatController.update(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseFlatUpdate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/delete") {
                try {
                    val query = call.receive<PsRequestFlatDelete>()
                    call.respond(flatController.delete(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseFlatDelete(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/list") {
                try {
                    val query = call.receive<PsRequestFlatList>()
                    call.respond(flatController.list(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseFlatList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
        }
    }
}
