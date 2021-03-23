package ru.otus.otuskotlin.propertysale.be.mappers.ps.exceptions

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus

class WrongBePsContextStatus(status: BePsContextStatus) :
    RuntimeException("Generated status ${status.name} must not appear in transport mappers")
