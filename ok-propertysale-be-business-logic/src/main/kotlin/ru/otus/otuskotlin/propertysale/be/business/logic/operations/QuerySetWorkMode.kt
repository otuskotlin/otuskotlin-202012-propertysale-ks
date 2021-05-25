package ru.otus.otuskotlin.propertysale.be.business.logic.operations

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.be.common.models.common.PsWorkMode
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation

object QuerySetWorkMode : IOperation<BePsContext> by operation({
    startIf { status == BePsContextStatus.RUNNING }
    execute {
        flatRepo = when (workMode) {
            PsWorkMode.TEST -> flatRepoTest
            PsWorkMode.PROD -> flatRepoProd
        }
        houseRepo = when (workMode) {
            PsWorkMode.TEST -> houseRepoTest
            PsWorkMode.PROD -> houseRepoProd
        }
        roomRepo = when (workMode) {
            PsWorkMode.TEST -> roomRepoTest
            PsWorkMode.PROD -> roomRepoProd
        }
    }
})
