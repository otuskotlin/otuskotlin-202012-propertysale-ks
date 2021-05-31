package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.room

import ru.otus.otuskotlin.propertysale.be.business.logic.helpers.validation
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.QuerySetWorkMode
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.room.RoomReadStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.context.PsPermission
import ru.otus.otuskotlin.propertysale.be.common.models.common.IPsError
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsError
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsPrincipalModel
import ru.otus.otuskotlin.propertysale.be.common.models.room.BePsRoomModel
import ru.otus.otuskotlin.propertysale.mp.common.validation.validators.ValidatorStringNonEmpty
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object RoomRead : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    // Установка параметров контекста в зависимости от режима работы в запросе
    execute(QuerySetWorkMode)

    // Обработка стабового запроса
    execute(RoomReadStub)

    // Валидация учетных данных
    operation {
        startIf { status == BePsContextStatus.RUNNING && useAuth }
        execute {
            if (principal == PsPrincipalModel.NONE) {
                errors.add(
                    PsError(
                        code = "unauthorized",
                        group = IPsError.Group.AUTH,
                        level = IPsError.Level.ERROR,
                        message = "User is unauthorized"
                    )
                )
                status = BePsContextStatus.ERROR
            }
        }
    }

    // Валидация параметров запроса
    validation {
        validate<String?> {
            on { requestRoomId.id }
            validator(
                ValidatorStringNonEmpty(
                    field = "room-id",
                    message = "Room ID requested must not be empty"
                )
            )
        }
    }

    // Чтение данных из репозитария, ответ сохраняется в контексте
    operation {
        startIf { status == BePsContextStatus.RUNNING }
        execute {
            try {
                roomRepo.read(this)
                status = BePsContextStatus.FINISHING
            } catch (t: Throwable) {
                status = BePsContextStatus.FAILING
                errors.add(
                    PsError(
                        code = "room-repo-read-error",
                        message = t.message ?: ""
                    )
                )
            }
        }
    }


    operation {
        startIf { status == BePsContextStatus.RUNNING && responseRoom.owner.id == principal.id }
        execute {
            permissions += PsPermission.READ
            permissions += PsPermission.UPDATE
            permissions += PsPermission.DELETE
        }
    }
    operation {
//        startIf { status == MpBeContextStatus.RUNNING && responseDemand.visibility == PUBLIC }
        execute {
            permissions += PsPermission.READ
        }
    }

    // применение разрешений
    operation {
        startIf { status == BePsContextStatus.RUNNING && useAuth }
        execute {
            if (!permissions.contains(PsPermission.READ)) {
                errors.add(
                    PsError(
                        code = "unauthorized",
                        group = IPsError.Group.AUTH,
                        level = IPsError.Level.ERROR,
                        message = "Operation is not permitted"
                    )
                )
                status = BePsContextStatus.ERROR
                responseRoom = BePsRoomModel.NONE
            }
        }
    }

    // Подготовка ответа
    execute(CompletePipeline)
})
