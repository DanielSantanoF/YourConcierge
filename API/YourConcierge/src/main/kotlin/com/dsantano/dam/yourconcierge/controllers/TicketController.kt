package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.repositories.TicketRepository
import com.dsantano.dam.yourconcierge.services.TicketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/ticket")
class TicketController(
        private val repo: TicketRepository,
        private val service: TicketService
) {

    @GetMapping("/{id}")
    fun getTicketById(@PathVariable id : UUID) : Optional<Ticket> {
        return repo.findById(id)
    }
}