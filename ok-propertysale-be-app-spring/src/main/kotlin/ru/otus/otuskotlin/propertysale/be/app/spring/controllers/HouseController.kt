package ru.otus.otuskotlin.propertysale.be.app.spring.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import java.time.Instant

class HouseController {
    fun list(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalList::class.java)
        val response = MpResponseProposalList(
            proposals = listOf(
                mockRead("p-001"),
                mockRead("p-002"),
                mockRead("p-003"),
                mockRead("p-004"),
                mockRead("p-005"),
                mockRead("p-006"),
            )
        )
        return ok().body(response)
    }

    fun create(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalCreate::class.java)
        val response = MpResponseProposalCreate(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposal = mockUpdate(
                id = "p-123",
                avatar = query.createData?.avatar,
                title = query.createData?.title,
                description = query.createData?.description
            )
        )
        return ok().body(response)
    }

    fun read(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalRead::class.java)
        val response = MpResponseProposalRead(
            responseId = "123",
            onRequest = query.requestId,
            endTime = Instant.now().toString(),
            status = ResponseStatusDto.SUCCESS,
            proposal = mockRead(query.proposalId ?: "")
        )
        return ok().body(response)
    }

    fun update(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalUpdate::class.java)
        val id = query.updateData?.id
        val response = if (id != null)
            MpResponseProposalUpdate(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                proposal = mockUpdate(
                    id = id,
                    avatar = query.updateData?.avatar,
                    title = query.updateData?.title,
                    description = query.updateData?.description
                )
            )
        else
            MpResponseProposalUpdate(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                errors = listOf(
                    ErrorDto(
                        code = "wrong-id",
                        group = "validation",
                        field = "id",
                        level = ErrorDto.Level.ERROR,
                        message = "id of the proposal to be updated cannot be empty"
                    )
                )
            )
        return ok().body(response)
    }

    fun delete(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalDelete::class.java)
        return ok().body(
            MpResponseProposalDelete(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                proposal = mockRead(query.proposalId ?: ""),
                deleted = true
            )
        )
    }

    fun offers(request: ServerRequest): ServerResponse {
        val query = request.body(MpRequestProposalOffersList::class.java)
        return ok().body(
            MpResponseProposalOffersList(
                responseId = "123",
                onRequest = query.requestId,
                endTime = Instant.now().toString(),
                status = ResponseStatusDto.SUCCESS,
                proposalDemands = listOf(
                    FlatController.mockRead("d-001"),
                    FlatController.mockRead("d-002"),
                    FlatController.mockRead("d-003"),
                    FlatController.mockRead("d-004"),
                )
            )
        )
    }

    companion object {
        fun mockUpdate(
            id: String,
            avatar: String?,
            title: String?,
            description: String?,
        ) = MpProposalDto(
            id = id,
            avatar = avatar,
            title = title,
            description = description,
            tagIds = setOf("t-001", "t-002"),
            techDets = setOf(
                TechDetsDto(
                    id = "tp-001",
                    param = TechParamDto(
                        id = "tp-001",
                        name = "Tech Param 001",
                        description = "Tech Param Description 001",
                        priority = 0.0,
                        units = setOf(RoomController.mockRead("ut-001", "kg")),
                    )
                )
            )
        )

        fun mockRead(id: String) = mockUpdate(
            id = id,
            avatar = "icon://menu",
            title = "Proposal $id",
            description = "Description of proposal $id"
        )
    }
}
