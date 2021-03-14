package ru.otus.otuskotlin.propertysale.be.app.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PsBeAppSpring

fun main(args: Array<String>) {
    runApplication<PsBeAppSpring>(*args)
}
