package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.entities.MyUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TicketRepository : JpaRepository<Ticket, UUID> {

    @Query("select t from Ticket t where t.myUser = :myUser")
    fun findAllTicketsByUser(myUser : MyUser) : List<Ticket>

}