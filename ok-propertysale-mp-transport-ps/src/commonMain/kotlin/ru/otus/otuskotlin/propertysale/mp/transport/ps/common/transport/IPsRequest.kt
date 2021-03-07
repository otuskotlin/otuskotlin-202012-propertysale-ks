package ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport

interface IPsRequest {
    val requestId: String?
    val onResponse: String?
    val startTime: String?
    val debug: IPsDebug?
}
