package ru.otus.otuskotlin.propertysale.mp.common.pipelines

interface IOperation<T> {
    suspend fun run(context: T)
}
