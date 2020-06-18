package com.dsantano.yourconciergeapp.repository

import androidx.lifecycle.MutableLiveData
import com.dsantano.yourconciergeapp.api.YourConciergeService
import com.dsantano.yourconciergeapp.api.response.tickets.NewTicket
import com.dsantano.yourconciergeapp.api.response.tickets.Ticket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(var yourConciergeService: YourConciergeService){
    var ticket: MutableLiveData<Ticket> = MutableLiveData()
    var ticketList: MutableLiveData<List<Ticket>> = MutableLiveData()

    suspend fun getAllTickets() = yourConciergeService.getAllTickets()

    suspend fun getMyTickets() = yourConciergeService.getMyTickets()

    suspend fun getTicketById(id: String) = yourConciergeService.getTicketById(id)

    suspend fun postNewTicket(req : NewTicket) = yourConciergeService.postNewTicket(req)

    suspend fun editTicketById(id: String, req: NewTicket) = yourConciergeService.editTicketById(id, req)

    suspend fun deleteTicketById(id: String) = yourConciergeService.deleteTicketById(id)
}