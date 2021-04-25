package ru.otus.otuskotlin.propertysale.backend.repository.cassandra.common.dto

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionIdModel
import ru.otus.otuskotlin.propertysale.be.common.models.common.BePsActionModel

@Entity
data class ActionCassandraDto(
    @CqlName(ID)
    val id: String? = null,
    @CqlName(TYPE)
    val type: String? = null,
    @CqlName(CONTENT)
    val content: String? = null,
    @CqlName(STATUS)
    val status: String? = null,
) {
    fun toModel() = BePsActionModel(
        id = id?.let { BePsActionIdModel(it) } ?: BePsActionIdModel.NONE,
        type = type ?: BePsActionModel.NONE.type,
        content = content ?: BePsActionModel.NONE.content,
        status = status ?: BePsActionModel.NONE.status,
    )

    companion object {
        const val TYPE_NAME = "actions"
        const val ID = "id"
        const val TYPE = "type"
        const val CONTENT = "content"
        const val STATUS = "status"

        fun of(model: BePsActionModel) = ActionCassandraDto(
            id = model.id.takeIf { it != BePsActionModel.NONE.id }?.id,
            type = model.type.takeIf { it != BePsActionModel.NONE.type },
            content = model.content.takeIf { it != BePsActionModel.NONE.content },
            status = model.status.takeIf { it != BePsActionModel.NONE.status },
        )
    }
}
