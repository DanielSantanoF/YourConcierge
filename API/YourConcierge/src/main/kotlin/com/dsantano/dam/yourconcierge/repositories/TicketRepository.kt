package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TicketRepository : JpaRepository<Ticket, UUID> {

    @Query("select t from Ticket t where t.user = :user")
    fun findAllTicketsByUser(user : User) : List<Ticket>

}