package ru.otus.otuskotlin.propertysale.be.common.models.common

enum class PsWorkMode {
    PROD,
    TEST;

    companion object {
        val DEFAULT = PROD
    }
}
