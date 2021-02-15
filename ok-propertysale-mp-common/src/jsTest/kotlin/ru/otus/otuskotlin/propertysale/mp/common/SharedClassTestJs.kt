package ru.otus.otuskotlin.propertysale.mp.common

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedClassTestJs {

    @Test
    fun sharedClass() {
        val sc = SharedClass()
        GlobalScope.launch {
            val res = sc.request("one")
            assertEquals("Js done", res)
        }
    }
}
