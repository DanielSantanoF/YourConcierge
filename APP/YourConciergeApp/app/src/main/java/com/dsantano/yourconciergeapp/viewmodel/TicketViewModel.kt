package com.dsantano.yourconciergeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsantano.yourconciergeapp.api.response.tickets.NewTicket
import com.dsantano.yourconciergeapp.api.response.tickets.Ticket
import com.dsantano.yourconciergeapp.common.Resource
import com.dsantano.yourconciergeapp.repository.TicketRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class TicketViewModel @Inject constructor(val ticketRepository: TicketRepository) : ViewModel(){

    var ticketList : MutableLiveData<Resource<List<Ticket>>> = MutableLiveData()
    var ticket : MutableLiveData<Resource<Ticket>> = MutableLiveData()
    var voidResponse :  MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun getAllTickets() = viewModelScope.launch {
        ticketList.value = Resource.Loading()
        val resp = ticketRepository.getAllTickets()
        ticketList.value =handleResponseList(resp)
    }

    fun getMyTickets() = viewModelScope.launch {
        ticketList.value = Resource.Loading()
        val resp = ticketRepository.getMyTickets()
        ticketList.value =handleResponseList(resp)
    }

    fun handleResponseList(response: Response<List<Ticket>>): Resource<List<Ticket>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error loading data")
    }

    fun getTicketById(id: String) = viewModelScope.launch {
        ticket.value = Resource.Loading()
        val resp = ticketRepository.getTicketById(id)
        ticket.value =handleResponse(resp)
    }

    fun postNewTicket(req : NewTicket) = viewModelScope.launch {
        ticket.value = Resource.Loading()
        val resp = ticketRepository.postNewTicket(req)
        ticket.value =handleResponse(resp)
    }

    fun editTicketById(id: String, req : NewTicket) = viewModelScope.launch {
        ticket.value = Resource.Loading()
        val resp = ticketRepository.editTicketById(id, req)
        ticket.value =handleResponse(resp)
    }

    fun handleResponse(response: Response<Ticket>): Resource<Ticket> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error("Error loading data")
    }

    fun deleteTicketById(id: String) = viewModelScope.launch {
        voidResponse.value = Resource.Loading()
        val resp = ticketRepository.deleteTicketById(id)
        voidResponse.value =handleVoidResponse(resp)
    }

    fun handleVoidResponse(response: Response<Void>): Resource<Unit> {
        if(response.isSuccessful) {
            return Resource.Success(Unit)
        }
        return Resource.Error("Error")
    }

}