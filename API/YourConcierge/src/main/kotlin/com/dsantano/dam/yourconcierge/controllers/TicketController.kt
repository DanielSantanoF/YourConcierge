package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.dtos.CreateTicketDTO
import com.dsantano.dam.yourconcierge.dtos.TicketDto
import com.dsantano.dam.yourconcierge.dtos.UpdateTicketDTO
import com.dsantano.dam.yourconcierge.dtos.toTicketDTO
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.repositories.TicketRepository
import com.dsantano.dam.yourconcierge.services.TicketService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/ticket")
class TicketController(
        private val repo: TicketRepository,
        private val service: TicketService
) {

    @GetMapping("/all")
    fun getAllTickets() : MutableList<Ticket> {
        return repo.findAll()
    }

    @GetMapping("/{id}")
    fun getTicketById(@PathVariable id : UUID) : Optional<Ticket> {
        return repo.findById(id)
    }

    @GetMapping("/mytickets")
    fun getAllUserTickets( @AuthenticationPrincipal myUser : MyUser): List<TicketDto> {
        return repo.findAllTicketsByUser( myUser ).map { it.toTicketDTO() }
    }

    @PostMapping("/new")
    fun newTicket(@AuthenticationPrincipal myUser : MyUser, @RequestBody newticket: CreateTicketDTO) : Optional<Ticket> {
        return service.create(newticket, myUser)
    }

    @PutMapping("/{id}")
    fun updateTicket(@RequestBody updateTicket : UpdateTicketDTO, @PathVariable id : UUID) : TicketDto {
        return repo.findById( id ).map { it ->
            val ticketUpdated : Ticket = it.copy( incidence = updateTicket.incidence, description = updateTicket.description, urgent = updateTicket.urgent)
            repo.save(ticketUpdated).toTicketDTO()
        }.orElseThrow() {
            ResponseStatusException( HttpStatus.NOT_FOUND, "Not results found" )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTicket(@PathVariable id : UUID) : ResponseEntity<Void> {
        repo.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}