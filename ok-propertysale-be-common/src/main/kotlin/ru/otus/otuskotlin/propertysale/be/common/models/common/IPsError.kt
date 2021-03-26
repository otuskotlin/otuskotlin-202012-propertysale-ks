package ru.otus.otuskotlin.propertysale.be.common.models.common

interface IPsError {
    val code: String
    val group: Group
    val field: String
    val level: Level
    val message: String

    enum class Group(val alias: String) {
        NONE(""),
        SERVER("internal-server")
    }

    enum class Level(val weight: Int) {
        FATAL(90),
        ERROR(70),
        WARNING(40),
        INFO(20);

        val isError: Boolean
            get() = weight >= ERROR.weight

        val isWarning: Boolean
            get() = this == WARNING
    }
}
