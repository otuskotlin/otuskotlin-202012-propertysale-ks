package ru.otus.otuskotlin.propertysale.be.app.ktor.modules

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import ru.otus.otuskotlin.propertysale.be.app.ktor.controllers.HouseController
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.ResponseStatusDto
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.responses.PsResponseHouseUpdate

fun Application.houseModule(testing: Boolean = false) {

    val houseController = HouseController()

    routing {
        route("/house") {
            post("/read") {
                try {
                    val query = call.receive<PsRequestHouseRead>()
                    call.respond(houseController.read(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseHouseRead(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/create") {
                try {
                    val query = call.receive<PsRequestHouseCreate>()
                    call.respond(houseController.create(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseHouseCreate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/update") {
                try {
                    val query = call.receive<PsRequestHouseUpdate>()
                    call.respond(houseController.update(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseHouseUpdate(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/delete") {
                try {
                    val query = call.receive<PsRequestHouseDelete>()
                    call.respond(houseController.delete(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseHouseDelete(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
            post("/list") {
                try {
                    val query = call.receive<PsRequestHouseList>()
                    call.respond(houseController.list(query))
                } catch (e: Throwable) {
                    call.respond(
                        PsResponseHouseList(
                            status = ResponseStatusDto.BAD_REQUEST
                        )
                    )
                }
            }
        }
    }
}
