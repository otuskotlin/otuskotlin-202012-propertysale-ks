package ru.otus.otuskotlin.propertysale.pipelines.validation

import ru.otus.otuskotlin.propertysale.mp.pipelines.Pipeline

fun <C> Pipeline.Builder<C>.validation(block: ValidationBuilder<C>.() -> Unit) {
    execute(ValidationBuilder<C>().apply(block).build())
}
