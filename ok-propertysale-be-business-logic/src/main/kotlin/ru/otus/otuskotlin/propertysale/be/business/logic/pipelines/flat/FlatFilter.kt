package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat

import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.QuerySetWorkMode
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat.FlatFilterStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatFilter : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(FlatFilterStub)

    // Получение списка по фильтру из репозитария, ответ сохраняется в контексте
    operation {
        startIf { status == BePsContextStatus.RUNNING }
        execute {
            try {
                flatRepo.list(this)
                status = BePsContextStatus.FINISHING
            } catch (t: Throwable) {
                status = BePsContextStatus.FAILING
                errors.add(
                    PsError(
                        code = "flat-repo-list-error",
                        message = t.message ?: ""
                    )
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
