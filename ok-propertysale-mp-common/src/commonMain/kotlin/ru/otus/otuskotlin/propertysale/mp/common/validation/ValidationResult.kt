package ru.otus.otuskotlin.propertysale.mp.common.validation

class ValidationResult(val errors: List<IValidationError>) {
    val isSuccess: Boolean
        get() = errors.isEmpty()

    companion object {
        val SUCCESS = ValidationResult(emptyList())
    }
}
