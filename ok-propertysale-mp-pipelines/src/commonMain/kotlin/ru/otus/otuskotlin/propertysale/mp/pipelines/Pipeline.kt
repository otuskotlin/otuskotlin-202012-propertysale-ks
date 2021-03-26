package ru.otus.otuskotlin.propertysale.mp.pipelines

class Pipeline<T>
private constructor(
    private val operations: Collection<IOperation<T>>,
    private val checkPrecondition: Predicate<T>,
    private val handleError: ErrorHandler<T>
) : IOperation<T> {
    override suspend fun execute(context: T) {
        try {
            if (checkPrecondition(context)) operations.forEach { it.execute(context) }
        } catch (throwable: Throwable) {
            handleError(context, throwable)
        }
    }

    @PipelineDsl
    class Builder<T>: IOperationBuilder<T> {
        private val operations: MutableList<IOperation<T>> = mutableListOf()
        private var checkPrecondition: Predicate<T> = { true }
        private var handleError: ErrorHandler<T> = { throw it }

        fun execute(operation: IOperation<T>) {
            operations.add(operation)
        }

        fun execute(block: Runnable<T>) {
            execute(Operation.Builder<T>().apply { execute(block) }.build())
        }

        fun startIf(block: Predicate<T>) {
            checkPrecondition = block
        }

        fun onError(block: ErrorHandler<T>) {
            handleError = block
        }

        override fun build(): Pipeline<T> =
            Pipeline(operations, checkPrecondition, handleError)
    }
}
