package ru.otus.otuskotlin.propertysale.mp.common.pipelines

typealias Runnable<T> = suspend T.() -> Unit

typealias Predicate<T> = suspend T.() -> Boolean

typealias ErrorHandler<T> = suspend T.(Throwable) -> Unit
