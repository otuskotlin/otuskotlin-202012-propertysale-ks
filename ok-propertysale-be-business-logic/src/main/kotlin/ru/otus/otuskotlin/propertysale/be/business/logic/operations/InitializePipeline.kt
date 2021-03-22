package ru.otus.otuskotlin.propertysale.be.business.logic.operations

import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContextStatus
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.operation

object InitializePipeline : IOperation<BePsContext> by operation({
    startIf { status == BePsContextStatus.NONE }
    execute { status = BePsContextStatus.RUNNING }
})
