package ru.otus.otuskotlin.propertysale.be.app.ktor.config

import io.ktor.application.*
import io.ktor.util.*

data class CassandraConfig(
    val hosts: String = "localhost",
    val port: Int = 9042,
    val user: String = "cassandra",
    val pass: String = "cassandra",
    val keyspace: String = "test_keyspace"
) {
    @OptIn(KtorExperimentalAPI::class)
    constructor(environment: ApplicationEnvironment) : this(
        hosts = environment.config.propertyOrNull("$PATH.hosts")?.getString() ?: "localhost",
        port = environment.config.propertyOrNull("$PATH.port")?.getString()?.toInt() ?: 9042,
        user = environment.config.propertyOrNull("$PATH.user")?.getString() ?: "cassandra",
        pass = environment.config.propertyOrNull("$PATH.pass")?.getString() ?: "cassandra",
        keyspace = environment.config.propertyOrNull("$PATH.keyspace")?.getString() ?: "test_keyspace"
    )

    companion object {
        const val PATH = "ktor.repository.cassandra"
    }
}
