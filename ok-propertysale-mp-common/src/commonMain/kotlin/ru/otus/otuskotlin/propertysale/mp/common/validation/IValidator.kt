package ru.otus.otuskotlin.propertysale.mp.common.validation

interface IValidator<T> {
    infix fun validate(sample: T): ValidationResult
}
