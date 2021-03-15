package ru.otus.otuskotlin.propertysale.be.business.logic.room

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.common.pipelines.pipeline

class RoomGetPipeline {
    suspend fun run(context: BePsContext) =
        PIPELINE.run(context.apply {
            // Подготовка контекста (внедрение зависимостей, установка вспомогательных объектов)
        })

    companion object {
        private val PIPELINE = pipeline<BePsContext> {
            run { status = BePsContextStatus.RUNNING }

            // Валидация

            // Обработка и работа с БД

            // Подготовка ответа
            run {
                responseRoom = BePsRoomModel(
                    id = BePsRoomIdModel("room-test-id"),
                    name = "room-test-name",
                    description = "room-test-description",
                    length = 7.0,
                    width = 5.0,
                    actions = mutableSetOf(
                        BePsActionModel(BePsActionIdModel("1"))
                    )
                )
                status = BePsContextStatus.SUCCESS
            }
        }
    }
}
