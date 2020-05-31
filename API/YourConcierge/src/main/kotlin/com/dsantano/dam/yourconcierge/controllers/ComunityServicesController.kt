package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.repositories.ComunityServicesRepository
import com.dsantano.dam.yourconcierge.services.ComunityServicesService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/comunityservices")
class ComunityServicesController(
        private val repo: ComunityServicesRepository,
        private val service: ComunityServicesService
) {

    @GetMapping("/{id}")
    fun getTicketById(@PathVariable id : UUID) : Optional<ComunityServices> {
        return repo.findById(id)
    }

}