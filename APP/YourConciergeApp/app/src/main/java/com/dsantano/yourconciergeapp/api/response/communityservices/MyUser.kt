package com.dsantano.yourconciergeapp.api.response.communityservices

data class MyUser(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val credentialsNonExpired: Boolean,
    val enabled: Boolean,
    val floor: String,
    val fullName: String,
    val id: String,
    val number: String,
    val password: String,
    val roles: List<String>,
    val username: String
)