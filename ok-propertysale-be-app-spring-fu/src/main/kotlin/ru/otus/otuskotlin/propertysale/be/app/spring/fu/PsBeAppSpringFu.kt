package ru.otus.otuskotlin.propertysale.be.app.spring.fu

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.FlatController
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.HouseController
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.RoomController
import ru.otus.otuskotlin.propertysale.be.business.logic.FlatCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.HouseCrud
import ru.otus.otuskotlin.propertysale.be.business.logic.RoomCrud
import ru.otus.otuskotlin.propertysale.mp.common.RestEndpoints

val app = webApplication {
    beans {
        bean<FlatCrud>()
        bean<HouseCrud>()
        bean<RoomCrud>()

        bean<FlatController>()
        bean<HouseController>()
        bean<RoomController>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080

        router {
            val flatController = ref<FlatController>()
            POST(RestEndpoints.flatList, flatController::list)
            POST(RestEndpoints.flatCreate, flatController::create)
            POST(RestEndpoints.flatRead, flatController::read)
            POST(RestEndpoints.flatUpdate, flatController::update)
            POST(RestEndpoints.flatDelete, flatController::delete)

            val houseController = ref<HouseController>()
            POST(RestEndpoints.houseList, houseController::list)
            POST(RestEndpoints.houseCreate, houseController::create)
            POST(RestEndpoints.houseRead, houseController::read)
            POST(RestEndpoints.houseUpdate, houseController::update)
            POST(RestEndpoints.houseDelete, houseController::delete)

            val roomController = ref<RoomController>()
            POST(RestEndpoints.roomList, roomController::list)
            POST(RestEndpoints.roomCreate, roomController::create)
            POST(RestEndpoints.roomRead, roomController::read)
            POST(RestEndpoints.roomUpdate, roomController::update)
            POST(RestEndpoints.roomDelete, roomController::delete)
        }
        converters {
            string()
            kotlinSerialization()
        }
    }
}

fun main() {
    app.run()
}
