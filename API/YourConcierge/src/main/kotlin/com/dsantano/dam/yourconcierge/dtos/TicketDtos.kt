package com.dsantano.dam.yourconcierge.dtos

import com.dsantano.dam.yourconcierge.entities.Ticket
import java.time.LocalDate
import java.util.*

data class TicketDto(
        var incidence: String,
        var description: String,
        var urgent: Boolean,
        var open: Boolean? = null,
        var createdAt: LocalDate? = null,
        var lastUpdate: LocalDate? = null,
        val id: UUID? = null
)

fun Ticket.toTicketDTO() = TicketDto(incidence, description, urgent, open, createdAt, lastUpdate, id)

data class CreateTicketDTO(
        var incidence: String,
        var description: String,
        var urgent: Boolean
)

data class UpdateTicketDTO(
        var incidence: String,
        var description: String,
        var urgent: Boolean
)