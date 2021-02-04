package ru.otus.otuskotlin.propertysale.mp.common

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class SharedClassTestJvm {

    @Test
    fun sharedClass() = runBlocking<Unit> {
        val sc = SharedClass()
        val res = sc.request("one")
        assertEquals("Jvm done", res)
    }
}
