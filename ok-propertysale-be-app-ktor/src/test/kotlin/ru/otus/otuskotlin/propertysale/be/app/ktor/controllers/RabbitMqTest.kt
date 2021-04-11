package ru.otus.otuskotlin.propertysale.be.app.ktor.controllers

import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import io.ktor.config.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testcontainers.containers.RabbitMQContainer
import ru.otus.otuskotlin.propertysale.be.app.ktor.config.jsonConfig
import ru.otus.otuskotlin.propertysale.be.app.ktor.module
import ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport.PsMessage
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.requests.PsRequestFlatList
import ru.otus.otuskotlin.propertysale.mp.transport.ps.flat.responses.PsResponseFlatList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RabbitMqTest {

    @Test
    fun flatListTest() {
        withTestApplication({
            // Выполняем настройки вместо файла application.conf
            (environment.config as MapApplicationConfig).apply {
                put("propertysale.rabbitmq.endpoint", "amqp://guest:guest@localhost:$rabbitMqTestPort")
                put("propertysale.rabbitmq.queueIn", queueIn)
                put("propertysale.rabbitmq.exchangeIn", exchangeIn)
                put("propertysale.rabbitmq.exchangeOut", exchangeOut)
            }

            // Запускаем наше приложение в виде расширения
            module(testing = true)
        }) {

            // Выполняем соединение с RabbitMQ
            ConnectionFactory().apply {
                host = "localhost"
                port = rabbitMqTestPort
            }.newConnection().use { connection ->
                connection.createChannel().use { channel ->

                    // Сюда сохраняем результат из консьюмера
                    var responseJson = ""

                    channel.exchangeDeclare(exchangeOut, "fanout", true)
                    channel.queueDeclare(queueOut, true, false, false, null)
                    channel.queueBind(queueOut, exchangeOut, "")

                    val deliverCallback = DeliverCallback { consumerTag, delivery ->
                        responseJson = String(delivery.getBody(), Charsets.UTF_8)
                        println(" [x] Received by $consumerTag: '$responseJson'")
                    }
                    channel.basicConsume(queueOut, true, deliverCallback, CancelCallback {})

                    channel.exchangeDeclare(exchangeIn, "fanout", true)
                    channel.basicPublish(
                        exchangeIn,
                        "",
                        null,
                        jsonConfig.encodeToString(
                            PsMessage.serializer(),
                            PsRequestFlatList(
                                requestId = "testFlatRequestId",
                                debug = PsRequestFlatList.Debug(
                                    stubCase = PsRequestFlatList.StubCase.SUCCESS
                                )
                            )
                        ).toByteArray()
                    )

                    runBlocking {
                        withTimeoutOrNull(250L) {
                            while (responseJson.isBlank()) {
                                delay(10)
                            }
                        }
                    }
                    println("RESPONSE: $responseJson")
                    assertTrue("No response from server") { responseJson.isNotBlank() }
                    val response =
                        jsonConfig.decodeFromString(PsMessage.serializer(), responseJson) as PsResponseFlatList
                    assertEquals("testFlatRequestId", response.onRequest)

                }
            }
        }
    }

    companion object {
        val queueIn = "mpQueueIn"
        val queueOut = "mpQueueOut"
        val exchangeIn = "mpExchangeIn"
        val exchangeOut = "mpExchangeOut"

        val container by lazy {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
            RabbitMQContainer("rabbitmq:latest").apply {
                withExchange(exchangeIn, "fanout")
                withExchange(exchangeOut, "fanout")
                withQueue(queueIn, false, true, null)
                withBinding(exchangeIn, queueIn)
                withExposedPorts(5672, 15672)
                start()
//                println("CONTAINER PORT: ${this.httpPort}, ${this.amqpPort}, ${this.amqpsPort}")
//                println("CONTAINER URL: ${this.httpUrl}, ${this.amqpUrl}, ${this.amqpsUrl}")
            }
        }

        val rabbitMqTestPort by lazy {
            container.getMappedPort(5672)
        }

    }
}
