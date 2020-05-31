package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ComunityServicesRepository : JpaRepository<ComunityServices, UUID> {

    @Query("select c from ComunityServices c where c.user = :user")
    fun findSericesByUser(user : User) : ComunityServices

}