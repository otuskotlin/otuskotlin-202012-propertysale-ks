package ru.otus.otuskotlin.propertysale.mp.pipelines

import ru.otus.otuskotlin.propertysale.mp.common.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PipelineTest {
    @Test
    fun simplePipeline() {
        val givenOperation = operation<TestContext> {
            execute { a += "c" }
        }
        val givenPipeline = pipeline<TestContext> {
            execute { a = "a" }
            execute(givenOperation)
            operation {
                startIf {
                    println("Check a: $a")
                    a.isNotEmpty()
                }
                execute { a += "b" }
            }
        }
        val givenContext = TestContext()

        runBlockingTest {
            println("Starting test")
            givenPipeline.execute(givenContext)
            assertEquals("acb", givenContext.a)
            println("Test Done")
            println("Finish test")
        }
    }

    @Test
    fun nestedPipeline() {
        val givenOperation = operation<TestContext> {
            execute { a += "c" }
        }
        val givenPipeline = pipeline<TestContext> {
            execute { a = "a" }
            execute(givenOperation)
            pipeline {
                startIf {
                    println("Check a: $a")
                    a.isNotEmpty()
                }
                execute { a += "b" }
            }
        }
        val givenContext = TestContext()

        runBlockingTest {
            println("Starting test")
            givenPipeline.execute(givenContext)
            assertEquals("acb", givenContext.a)
            println("Test Done")
            println("Finish test")
        }
    }

    data class TestContext(
        var a: String = ""
    )
}
