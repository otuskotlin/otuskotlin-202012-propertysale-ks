package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.QuerySetWorkMode
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat.FlatDeleteStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatDelete : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(FlatDeleteStub)

    // Валидация параметров запроса
    validation {
        validate<String?> {
            on { requestFlatId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "flat-id",
                    message = "Flat ID requested must not be empty"
                )
            )
        }
    }

    // Удаление из репозитария, ответ сохраняется в контексте
    operation {
        startIf { status == BePsContextStatus.RUNNING }
        execute {
            try {
                flatRepo.delete(this)
                status = BePsContextStatus.FINISHING
            } catch (t: Throwable) {
                status = BePsContextStatus.FAILING
                errors.add(
                    PsError(
                        code = "flat-repo-delete-error",
                        message = t.message ?: ""
                    )
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
