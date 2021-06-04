package ru.otus.otuskotlin.propertysale.be.app.ktor.config

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.requests.PsRequestFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.flat.responses.PsResponseFlatUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.requests.PsRequestHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.house.responses.PsResponseHouseUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.requests.PsRequestRoomUpdate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomCreate
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomDelete
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomRead
import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.room.responses.PsResponseRoomUpdate

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
