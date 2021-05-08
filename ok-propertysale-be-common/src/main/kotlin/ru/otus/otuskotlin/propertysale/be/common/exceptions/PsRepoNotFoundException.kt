package ru.otus.otuskotlin.propertysale.be.common.exceptions

class PsRepoNotFoundException(id: String): RuntimeException("Object with ID=$id is not found")
