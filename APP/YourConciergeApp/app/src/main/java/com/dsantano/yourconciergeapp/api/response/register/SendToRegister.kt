package com.dsantano.yourconciergeapp.api.response.register

class SendToRegister(
    val username: String,
    val fullName: String,
    val floor: String,
    val number: String,
    val password: String,
    val password2: String
) {
}