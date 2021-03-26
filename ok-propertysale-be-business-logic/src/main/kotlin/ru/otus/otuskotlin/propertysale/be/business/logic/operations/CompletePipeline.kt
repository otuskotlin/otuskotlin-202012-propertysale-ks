package ru.otus.otuskotlin.propertysale.be.business.logic.operations

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object CompletePipeline : IOperation<BePsContext> by pipeline({
    operation {
        startIf { status in setOf(BePsContextStatus.RUNNING, BePsContextStatus.FINISHING) }
        execute { status = BePsContextStatus.SUCCESS }
    }
    operation {
        startIf { status != BePsContextStatus.SUCCESS }
        execute { status = BePsContextStatus.ERROR }
    }
})
