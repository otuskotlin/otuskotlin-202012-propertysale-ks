package ru.otus.otuskotlin.propertysale.mp.common.pipelines

class Operation<T>
private constructor(
    private val runOperation: Runnable<T>,
    private val checkPrecondition: Predicate<T>,
    private val handleError: ErrorHandler<T>,
) : IOperation<T> {
    override suspend fun run(context: T) {
        try {
            if (checkPrecondition(context)) runOperation(context)
        } catch (t: Throwable) {
            handleError(context, t)
        }
    }

    @PipelineDsl
    class Builder<T> {
        private var runOperation: Runnable<T> = {}
        private var checkPrecondition: Predicate<T> = { true }
        private var handleError: ErrorHandler<T> = { throw it }

        fun run(block: Runnable<T>) {
            runOperation = block
        }

        fun startIf(blocK: Predicate<T>) {
            checkPrecondition = blocK
        }

        fun onError(block: ErrorHandler<T>) {
            handleError = block
        }

        fun build(): Operation<T> =
            Operation(runOperation, checkPrecondition, handleError)
    }
}
