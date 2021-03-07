package ru.otus.otuskotlin.propertysale.be.app.spring.fu

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.FlatController
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.HouseController
import ru.otus.otuskotlin.propertysale.be.app.spring.fu.controllers.RoomController

val app = webApplication {
    beans {
        bean<FlatController>()
        bean<HouseController>()
        bean<RoomController>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080

        router {
            val flatController = ref<FlatController>()
            POST("/flat/list", flatController::list)
            POST("/flat/create", flatController::create)
            POST("/flat/read", flatController::read)
            POST("/flat/update", flatController::update)
            POST("/flat/delete", flatController::delete)

            val houseController = ref<HouseController>()
            POST("/house/list", houseController::list)
            POST("/house/create", houseController::create)
            POST("/house/read", houseController::read)
            POST("/house/update", houseController::update)
            POST("/house/delete", houseController::delete)

            val roomController = ref<RoomController>()
            POST("/room/list", roomController::list)
            POST("/room/create", roomController::create)
            POST("/room/read", roomController::read)
            POST("/room/update", roomController::update)
            POST("/room/delete", roomController::delete)
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
