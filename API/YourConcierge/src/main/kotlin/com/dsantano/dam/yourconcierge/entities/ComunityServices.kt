package com.dsantano.dam.yourconcierge.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import java.util.*
import javax.persistence.*

@Entity
data class ComunityServices(
        @Column(name = "trashBags")
        var trashBags: Boolean,
        @Column(name = "cleanFloor")
        var cleanFloor: Boolean,
        @JsonBackReference
        @OneToOne
        var myUser: MyUser? = null,
        @Id @GeneratedValue val id: UUID? = null
)