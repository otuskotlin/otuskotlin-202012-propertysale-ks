package ru.otus.otuskotlin.propertysale.be.app.spring

import org.springframework.context.support.beans
import ru.otus.otuskotlin.propertysale.be.app.spring.controllers.FlatController

val app = webApplication {
    beans {
        bean<FlatController>()
    }
    webMvc {
        port = if (profiles.contains("test")) 8181 else 8080
        router {
            val demandService = ref<FlatController>()
            POST("/demand/list", demandService::list)
            POST("/demand/create", demandService::create)
            POST("/demand/read", demandService::read)
            POST("/demand/update", demandService::update)
            POST("/demand/delete", demandService::delete)
            POST("/demand/offers", demandService::offers)

            val proposalService = ref<ProposalController>()
            POST("/proposal/list", proposalService::list)
            POST("/proposal/create", proposalService::create)
            POST("/proposal/read", proposalService::read)
            POST("/proposal/update", proposalService::update)
            POST("/proposal/delete", proposalService::delete)
            POST("/proposal/offers", proposalService::offers)
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
