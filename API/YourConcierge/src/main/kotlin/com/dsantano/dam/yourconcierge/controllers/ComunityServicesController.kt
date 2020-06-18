package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.dtos.*
import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.repositories.ComunityServicesRepository
import com.dsantano.dam.yourconcierge.services.ComunityServicesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/comunityservices")
class ComunityServicesController(
        private val repo: ComunityServicesRepository,
        private val service: ComunityServicesService
) {

    @PostMapping("/new")
    fun newCommunityService(@AuthenticationPrincipal myUser : MyUser, @RequestBody newComServ: CreateComunityServiceDTO) : Optional<ComunityServices> {
        return service.create(newComServ, myUser)
    }
    
    @GetMapping("/{id}")
    fun getCommunityServiceById(@PathVariable id : UUID) : Optional<ComunityServices> {
        return repo.findById(id)
    }

    @PutMapping("/{id}")
    fun updateCommunityService(@RequestBody updateCommunityServices : UpdateComunityServiceDTO, @PathVariable id : UUID) : ComunityServicesDto? {
        return repo.findById( id ).map { it ->
            val comservUpdated : ComunityServices = it.copy( trashBags = updateCommunityServices.trashBags, cleanFloor = updateCommunityServices.cleanFloor )
            repo.save(comservUpdated).toComunityServiceDTO()
        }.orElseThrow() {
            ResponseStatusException( HttpStatus.NOT_FOUND, "Not results found" )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteComServices(@PathVariable id : UUID) : ResponseEntity<Void> {
        repo.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/mycomunityservices")
    fun getMyUserCommunityServices( @AuthenticationPrincipal myUser : MyUser): ComunityServicesDto {
        return repo.findAllComunitySericesByUser( myUser ).toComunityServiceDTO()
    }

    @GetMapping("/byuser/{id}")
    fun getUserCommunityServicesById( @PathVariable id : UUID ): ComunityServicesDto {
        return repo.findComunitySericesByUserId( id ).toComunityServiceDTO()
    }

}