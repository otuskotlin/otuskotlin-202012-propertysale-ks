package ru.otus.otuskotlin.propertysale.be.common.exceptions

class PsRepoWrongIdException(id: String): Throwable("Wrong ID in operation: $id")
