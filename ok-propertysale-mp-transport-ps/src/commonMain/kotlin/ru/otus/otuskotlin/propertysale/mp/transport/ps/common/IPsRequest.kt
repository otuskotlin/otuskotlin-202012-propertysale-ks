package ru.otus.otuskotlin.propertysale.mp.transport.ps.common

interface IPsRequest {
    val requestId: String?
    val onResponse: String?
    val startTime: String?
    val debug: IPsDebug?
}