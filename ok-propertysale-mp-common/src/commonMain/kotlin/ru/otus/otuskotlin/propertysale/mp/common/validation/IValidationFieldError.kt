package ru.otus.otuskotlin.propertysale.mp.common.validation

interface IValidationFieldError : IValidationError {
    val field: String
}
