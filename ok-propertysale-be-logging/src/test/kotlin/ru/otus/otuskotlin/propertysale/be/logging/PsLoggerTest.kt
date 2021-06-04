package ru.otus.otuskotlin.propertysale.be.logging

import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class PsLoggerTest {

    @Test
    fun loggerInit() {
        runBlocking {
            val logger = psLogger(this::class.java)
            logger.doWithLoggingSusp(logId = "test-logger") {
                println("Some action")
            }
        }
    }
}
