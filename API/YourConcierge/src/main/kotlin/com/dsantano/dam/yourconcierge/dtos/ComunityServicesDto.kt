package com.dsantano.dam.yourconcierge.dtos


import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.MyUser
import java.util.*

data class ComunityServicesDto(
        var trashBags: Boolean,
        var cleanFloor: Boolean,
        var myUser: MyUser? = null,
        val id: UUID? = null
)

fun ComunityServices.toComunityServiceDTO() = ComunityServicesDto(trashBags, cleanFloor, myUser, id)

data class CreateComunityServiceDTO(
        var trashBags: Boolean,
        var cleanFloor: Boolean
)

data class UpdateComunityServiceDTO(
        var trashBags: Boolean,
        var cleanFloor: Boolean
)