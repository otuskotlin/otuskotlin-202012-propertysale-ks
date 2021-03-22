package ru.otus.otuskotlin.propertysale.be.common.models.common

data class PsError(
    override val code: String = "",
    override val group: IPsError.Group = IPsError.Group.NONE,
    override val field: String = "",
    override val level: IPsError.Level = IPsError.Level.ERROR,
    override val message: String = ""
) : IPsError
