package ru.otus.otuskotlin.propertysale.mp.common

import kotlin.test.Test
import kotlin.test.assertTrue

internal class SomeFunTest {
    @Test
    fun someFunTest1() {
        val str = "OneTwoThree"
        assertTrue("Result contains parameter string") {
            someFun(str).contains(str)
        }
    }
}
