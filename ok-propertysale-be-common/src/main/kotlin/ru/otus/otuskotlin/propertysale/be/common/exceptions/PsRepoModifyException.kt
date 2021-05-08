package ru.otus.otuskotlin.propertysale.be.common.exceptions

class PsRepoModifyException(id: String) : Throwable("Cannot modify record with id = $id")
