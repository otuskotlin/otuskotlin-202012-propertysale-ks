package ru.otus.otuskotlin.propertysale.be.business.logic.pipelines.house

import ru.otus.otuskotlin.propertysale.be.business.logic.operations.CompletePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.InitializePipeline
import ru.otus.otuskotlin.propertysale.be.business.logic.operations.stubs.house.HouseFilterStub
import ru.otus.otuskotlin.propertysale.be.common.context.BePsContext
import ru.otus.otuskotlin.propertysale.mp.pipelines.IOperation
import ru.otus.otuskotlin.propertysale.mp.pipelines.pipeline

object HouseFilter : IOperation<BePsContext> by pipeline({
    execute(InitializePipeline)

    execute(HouseFilterStub)

    execute(CompletePipeline)
})
