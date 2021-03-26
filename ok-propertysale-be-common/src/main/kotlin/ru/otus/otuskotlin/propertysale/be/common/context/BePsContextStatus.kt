package ru.otus.otuskotlin.propertysale.be.common.context

enum class BePsContextStatus {
    NONE,
    RUNNING,
    FINISHING,
    FAILING,
    SUCCESS,
    ERROR;

    val isError: Boolean
        get() = this in setOf(FAILING, ERROR)
}
