package com.dsantano.dam.yourconcierge.dtos

import com.dsantano.dam.yourconcierge.entities.MyUser
import java.util.*

data class UserDTO(
        var username : String,
        var fullName: String,
        var floor: String,
        var number: String,
        var roles: String,
        val id: UUID? = null
)

fun MyUser.toUserDTO() = UserDTO(username, fullName,  floor, number, roles.joinToString(), id)

data class CreateUserDTO(
        var username: String,
        var fullName: String,
        var floor: String,
        var number: String,
        val password: String,
        val password2: String
)

data class UpdateUserDTO(
        var username: String,
        var fullName: String,
        var floor: String,
        var number: String,
        val password: String
)