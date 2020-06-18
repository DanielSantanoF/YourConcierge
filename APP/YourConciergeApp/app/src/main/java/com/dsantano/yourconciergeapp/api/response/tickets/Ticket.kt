package com.dsantano.yourconciergeapp.api.response.tickets

data class Ticket(
    val createdAt: String,
    val description: String,
    val id: String,
    val incidence: String,
    val lastUpdate: String,
    val `open`: Boolean,
    val urgent: Boolean
)