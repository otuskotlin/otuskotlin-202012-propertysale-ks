package ru.otus.otuskotlin.propertysale.be.app.ktor.utils

import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.isActive
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.FlatService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.HouseService
import ru.otus.otuskotlin.propertysale.be.app.ktor.services.RoomService
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.room.requests.PsRequestRoomUpdate

suspend fun service(
    context: BePsContext,
    query: PsMessage?,
    flatService: FlatService,
    houseService: HouseService,
    roomService: RoomService
): PsMessage? = when (query) {
    is PsRequestFlatList -> flatService.list(context, query)
    is PsRequestFlatCreate -> flatService.create(context, query)
    is PsRequestFlatRead -> flatService.read(context, query)
    is PsRequestFlatUpdate -> flatService.update(context, query)
    is PsRequestFlatDelete -> flatService.delete(context, query)

    is PsRequestHouseList -> houseService.list(context, query)
    is PsRequestHouseCreate -> houseService.create(context, query)
    is PsRequestHouseRead -> houseService.read(context, query)
    is PsRequestHouseUpdate -> houseService.update(context, query)
    is PsRequestHouseDelete -> houseService.delete(context, query)

    is PsRequestRoomList -> roomService.list(context, query)
    is PsRequestRoomCreate -> roomService.create(context, query)
    is PsRequestRoomRead -> roomService.read(context, query)
    is PsRequestRoomUpdate -> roomService.update(context, query)
    is PsRequestRoomDelete -> roomService.delete(context, query)

    else ->
        // В дальнейшем здесь должен оказаться чейн обработки ошибок, и других событий
        when {
            context.status == BePsContextStatus.FAILING -> flatService.list(context, null)
            // Если содзана новая сессия
            (context.userSession.fwSession as? WebSocketSession)?.isActive == true -> flatService.list(
                context,
                PsRequestFlatList()
            )
            // Если удалена сессия
            (context.userSession.fwSession as? WebSocketSession)?.isActive == false -> null
            else -> null
        }
}
