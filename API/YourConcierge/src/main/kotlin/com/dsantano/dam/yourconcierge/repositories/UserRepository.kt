package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.MyUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<MyUser, UUID> {

    fun findByUsername(username : String) : Optional<MyUser>

    @Query("select u from MyUser u where u.id = :id")
    fun findUserById(id : UUID) : MyUser

}