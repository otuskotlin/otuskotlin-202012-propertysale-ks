package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.flat

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.QuerySetWorkMode
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.flat.FlatUpdateStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object FlatUpdate : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(FlatUpdateStub)

    // Валидация параметров запроса
    validation {
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "id",
                    message = "You must provide non-empty id for the flat"
                )
            )
            on { requestFlat.id.asString() }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "name",
                    message = "You must provide non-empty name for the flat"
                )
            )
            on { requestFlat.name }
        }
        validate<String?> {
            validator(
                ValidatorStringNonEmpty(
                    field = "description",
                    message = "You must provide non-empty description for the flat"
                )
            )
            on { requestFlat.description }
        }
    }

    // Обновление данных в репозитарии, ответ сохраняется в контексте
    operation {
        startIf { status == BePsContextStatus.RUNNING }
        execute {
            try {
                flatRepo.update(this)
                status = BePsContextStatus.FINISHING
            } catch (t: Throwable) {
                status = BePsContextStatus.FAILING
                errors.add(
                    PsError(
                        code = "flat-repo-update-error",
                        message = t.message ?: ""
                    )
                )
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
