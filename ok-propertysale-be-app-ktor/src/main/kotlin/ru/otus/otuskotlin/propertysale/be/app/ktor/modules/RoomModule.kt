package ru.otus.otuskotlin.propertysale.be.app.ktor.modules

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.RoomController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.responses.PsResponseRoomUpdate

fun Application.roomModule(testing: Boolean = false) {

    val roomController = RoomController()

    routing {
        route("/room") {
            post("/read") {
                try {
                    val query = call.receive<PsRequestRoomRead>()
                    call.respond(roomController.read(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseRoomRead(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/create") {
                try {
                    val query = call.receive<PsRequestRoomCreate>()
                    call.respond(roomController.create(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseRoomCreate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/update") {
                try {
                    val query = call.receive<PsRequestRoomUpdate>()
                    call.respond(roomController.update(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseRoomUpdate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/delete") {
                try {
                    val query = call.receive<PsRequestRoomDelete>()
                    call.respond(roomController.delete(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseRoomDelete(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/list") {
                try {
                    val query = call.receive<PsRequestRoomList>()
                    call.respond(roomController.list(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseRoomList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
        }
    }
}
