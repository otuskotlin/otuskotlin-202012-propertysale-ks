package ru.otus.otuskotlin.propertysale.mp.transport.ps.logs

import ru.otus.otuskotlin.propertysale.mp.transport.ps.models.common.transport.ErrorDto

/**
 *  Общая модель лога для всех микросервисов системы
 */
data class CommonLogModel(
    val messageId: String? = null,
    val messageTime: String? = null,
    val logId: String? = null,
    val source: String? = null,
    val propertysale: PsLogModel? = null,
    // поля для других сервисов
    val errors: List<ErrorDto>? = null,
)
