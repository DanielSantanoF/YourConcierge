package com.dsantano.dam.yourconcierge.services


import com.dsantano.dam.yourconcierge.dtos.CreateTicketDTO
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.repositories.TicketRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TicketService(
        private val repo: TicketRepository
) {

    fun create(newTicket: CreateTicketDTO, myUser: MyUser): Optional<Ticket> {
        return Optional.of(
                with(newTicket) {
                    repo.save(Ticket(incidence, description, urgent, true, myUser))
                }
        )
    }

}