package com.dsantano.yourconciergeapp.api.response.login

import com.dsantano.yourconciergeapp.api.response.users.User

data class LoginResponse(
    val refreshToken: String,
    val token: String,
    val user: User
)