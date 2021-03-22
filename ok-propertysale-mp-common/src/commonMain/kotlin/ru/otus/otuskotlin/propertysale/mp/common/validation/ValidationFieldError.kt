package ru.otus.otuskotlin.propertysale.mp.common.validation

data class ValidationFieldError(
    override val message: String,
    override val field: String,
) : IValidationError, IValidationFieldError
