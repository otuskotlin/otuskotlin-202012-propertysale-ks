package ru.otus.otuskotlin.propertysale.mp.pipelines

interface IOperation<T> {
    suspend fun execute(context: T)
}
