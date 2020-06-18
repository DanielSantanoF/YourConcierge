package com.dsantano.yourconciergeapp.api.response.register

data class RegisterResponse(
    val floor: String,
    val fullName: String,
    val id: String,
    val number: String,
    val roles: String,
    val username: String
)