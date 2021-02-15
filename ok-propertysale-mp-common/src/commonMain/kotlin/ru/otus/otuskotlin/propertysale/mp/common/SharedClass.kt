package ru.otus.otuskotlin.propertysale.mp.common

expect class SharedClass {
    suspend fun request(id: String): String
}
