package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.MyUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ComunityServicesRepository : JpaRepository<ComunityServices, UUID> {

    @Query("select c from ComunityServices c where c.myUser = :myUser")
    fun findAllComunitySericesByUser(myUser : MyUser) : ComunityServices

    @Query("select c from ComunityServices c where c.myUser.id = :id")
    fun findComunitySericesByUserId(id : UUID) : ComunityServices

    @Query("select c from ComunityServices c where c.id = :id")
    fun findComunitySericesById(id : UUID) : ComunityServices

}