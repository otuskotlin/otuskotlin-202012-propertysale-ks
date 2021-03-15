package ru.otus.otuskotlin.propertysale.be.business.logic.flat

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsActionModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.BePsFlatModel
import ru.otus.otuskotlin.propertysale.mp.common.pipelines.pipeline

class FlatFilterPipeline {
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
                responseFlats = mutableListOf(
                    BePsFlatModel(
                        id = BePsFlatIdModel("flat-test-id"),
                        name = "flat-test-name",
                        description = "flat-test-description",
                        floor = 5,
                        numberOfRooms = 2,
                        actions = mutableSetOf(
                            BePsActionModel(BePsActionIdModel("1")),
                            BePsActionModel(BePsActionIdModel("2")),
                            BePsActionModel(BePsActionIdModel("3"))
                        )
                    )
                )
                status = BePsContextStatus.SUCCESS
            }
        }
    }
}
