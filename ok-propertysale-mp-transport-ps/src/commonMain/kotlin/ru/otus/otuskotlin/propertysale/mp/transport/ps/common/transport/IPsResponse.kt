package ru.otus.otuskotlin.propertysale.mp.transport.ps.common.transport

interface IPsResponse {
    val responseId: String?
    val onRequest: String?
    val endTime: String?
    val errors: List<ErrorDto>?
    val status: ResponseStatusDto?
    val debug: IPsDebug?
}
