package com.dsantano.dam.yourconcierge.services

import com.dsantano.dam.yourconcierge.dtos.CreateComunityServiceDTO
import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.repositories.ComunityServicesRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ComunityServicesService(
        private val repo: ComunityServicesRepository
) {

    fun create(newComSer: CreateComunityServiceDTO, myUser: MyUser): Optional<ComunityServices> {
        return Optional.of(
                with(newComSer) {
                    repo.save(ComunityServices(trashBags, cleanFloor, myUser))
                }
        )
    }

}