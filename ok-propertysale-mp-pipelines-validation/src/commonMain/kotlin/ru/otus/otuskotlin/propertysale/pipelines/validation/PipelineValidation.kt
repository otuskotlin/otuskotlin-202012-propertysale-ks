package ru.otus.otuskotlin.propertysale.pipelines.validation

import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.Predicate

class PipelineValidation<C>(
    private val validations: List<IValidationOperation<C, *>>,
    private val checkPrecondition: Predicate<C> = { true },
) : IOperation<C> {
    override suspend fun execute(context: C) {
        if (context.checkPrecondition()) {
            validations.forEach {
                it.execute(context)
            }
        }
    }
}
