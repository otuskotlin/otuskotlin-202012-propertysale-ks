package ru.otus.otuskotlin.propertysale.be.business.logic.house

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsHouseModel
import ru.otus.otuskotlin.propertysale.mp.common.pipelines.pipeline

class HouseDeletePipeline {
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
                responseHouse = BePsHouseModel(
                    id = BePsHouseIdModel("house-test-id"),
                    name = "house-test-name",
                    description = "house-test-description",
                    area = 150.0,
                    actions = mutableSetOf(
                        BePsActionModel(BePsActionIdModel("1")),
                        BePsActionModel(BePsActionIdModel("2"))
                    )
                )
                status = BePsContextStatus.SUCCESS
            }
        }
    }
}
