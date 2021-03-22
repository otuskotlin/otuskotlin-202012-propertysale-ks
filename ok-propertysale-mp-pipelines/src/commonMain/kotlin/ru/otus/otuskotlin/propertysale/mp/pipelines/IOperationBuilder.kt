package ru.otus.otuskotlin.propertysale.mp.pipelines

interface IOperationBuilder<T> {
    fun build(): IOperation<T>
}
