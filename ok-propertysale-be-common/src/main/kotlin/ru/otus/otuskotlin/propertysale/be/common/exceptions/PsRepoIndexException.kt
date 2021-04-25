package ru.otus.otuskotlin.propertysale.be.common.exceptions

class PsRepoIndexException(index: String = "") : RuntimeException("Objects not found by index: $index")
