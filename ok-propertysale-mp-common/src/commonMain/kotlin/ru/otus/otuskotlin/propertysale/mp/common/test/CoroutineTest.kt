package ru.otus.otuskotlin.propertysale.mp.common.test

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

expect fun runBlockingTest(block: suspend CoroutineScope.()-> Unit)
expect val testCoroutineContext: CoroutineContext

//// JVM code:
//actual val ru.otus.otuskotlin.propertysale.mp.common.test.testCoroutineContext: CoroutineContext =
//    Executors.newSingleThreadExecutor().asCoroutineDispatcher()
//actual fun ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
//    runBlocking(ru.otus.otuskotlin.propertysale.mp.common.test.testCoroutineContext) { this.block() }
//
//// JS code:
//val testScope = MainScope()
//actual val ru.otus.otuskotlin.propertysale.mp.common.test.testCoroutineContext: CoroutineContext = testScope.coroutineContext
//actual fun ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest(block: suspend CoroutineScope.() -> Unit): dynamic = testScope.promise { this.block() }
//
////iOS code:
//actual val ru.otus.otuskotlin.propertysale.mp.common.test.testCoroutineContext: CoroutineContext =
//    newSingleThreadContext("testRunner")
//
//actual fun ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
//    runBlocking(ru.otus.otuskotlin.propertysale.mp.common.test.testCoroutineContext) { this.block() }
