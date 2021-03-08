package ru.otus.otuskotlin.propertysale.be.app.ktor.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
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

val jsonConfig: Json by lazy {
    Json {
        prettyPrint = true
        serializersModule = SerializersModule {
            polymorphic(PsMessage::class) {
                subclass(PsRequestFlatRead::class)
                subclass(PsRequestFlatList::class)
                subclass(PsRequestFlatCreate::class)
                subclass(PsRequestFlatUpdate::class)
                subclass(PsRequestFlatDelete::class)

                subclass(PsResponseFlatRead::class)
                subclass(PsResponseFlatList::class)
                subclass(PsResponseFlatCreate::class)
                subclass(PsResponseFlatUpdate::class)
                subclass(PsResponseFlatDelete::class)

                subclass(PsRequestHouseRead::class)
                subclass(PsRequestHouseList::class)
                subclass(PsRequestHouseCreate::class)
                subclass(PsRequestHouseUpdate::class)
                subclass(PsRequestHouseDelete::class)

                subclass(PsResponseHouseRead::class)
                subclass(PsResponseHouseList::class)
                subclass(PsResponseHouseCreate::class)
                subclass(PsResponseHouseUpdate::class)
                subclass(PsResponseHouseDelete::class)

                subclass(PsRequestRoomRead::class)
                subclass(PsRequestRoomList::class)
                subclass(PsRequestRoomCreate::class)
                subclass(PsRequestRoomUpdate::class)
                subclass(PsRequestRoomDelete::class)

                subclass(PsResponseRoomRead::class)
                subclass(PsResponseRoomList::class)
                subclass(PsResponseRoomCreate::class)
                subclass(PsResponseRoomUpdate::class)
                subclass(PsResponseRoomDelete::class)
            }
        }
        classDiscriminator = "type"
    }
}
