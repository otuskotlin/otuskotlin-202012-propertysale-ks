package ru.otus.otuskotlin.propertysale.mp.common.validation

data class ValidationDefaultError(
    override val message: String,
) : IValidationError
