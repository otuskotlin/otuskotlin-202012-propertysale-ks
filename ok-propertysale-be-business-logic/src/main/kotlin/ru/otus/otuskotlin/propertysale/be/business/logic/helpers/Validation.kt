package ru.otus.otuskotlin.propertysale.be.business.logic.helpers

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError
import ru.otus.otuskotlin.propertysale.mp.common.validation.ValidationResult
import ru.otus.otuskotlin.propertysale.mp.pipelines.Pipeline
import ru.otus.otuskotlin.propertysale.pipelines.validation.ValidationBuilder

fun Pipeline.Builder<BePsContext>.validation(block: ValidationBuilder<BePsContext>.() -> Unit) {
    execute(ValidationBuilder<BePsContext>()
        .apply {
            startIf { status == BePsContextStatus.RUNNING }
            errorHandler { vr: ValidationResult ->
                if (vr.isSuccess) return@errorHandler
                val errs = vr.errors.map { PsError(message = it.message) }
                errors.addAll(errs)
                status = BePsContextStatus.FAILING
            }
        }
        .apply(block)
        .build())
}
