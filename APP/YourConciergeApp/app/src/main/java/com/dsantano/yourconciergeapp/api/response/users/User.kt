package com.dsantano.yourconciergeapp.api.response.users

data class User(
    val floor: String,
    val fullName: String,
    val id: String,
    val number: String,
    val roles: String,
    val username: String
)