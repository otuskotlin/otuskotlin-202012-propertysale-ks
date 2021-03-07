package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto

class RoomController {

    companion object {
        fun mockRead(id: String, symbol: String) = UnitTypeDto(
            id = id,
            name = symbol,
            description = "Description of the $symbol",
            symbol = symbol,
            isBase = false
        )
    }
}
