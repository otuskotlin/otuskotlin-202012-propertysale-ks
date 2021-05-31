package ru.otus.otuskotlin.propertysale.be.logging

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

fun psLogger(loggerId: String): PsLogContext = psLogger(
    logger = LoggerFactory.getLogger(loggerId) as Logger
)

fun psLogger(clazz: Class<out Any>): PsLogContext = psLogger(
    logger = LoggerFactory.getLogger(clazz) as Logger
)

/**
 * Функция генерирует внутренний logger - объект класса MpLogContext
 *
 * @param logger интстанс логера Logback, полученный методом [[LoggerFactory.getLogger()]]
 */
fun psLogger(logger: Logger): PsLogContext = PsLogContext(
    logger = logger,
    loggerId = logger.name,
)
