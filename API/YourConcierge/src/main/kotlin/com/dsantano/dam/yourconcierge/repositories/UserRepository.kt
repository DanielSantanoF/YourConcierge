package com.dsantano.dam.yourconcierge.repositories

import com.dsantano.dam.yourconcierge.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsername(username : String) : Optional<User>

}