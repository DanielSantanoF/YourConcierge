package com.dsantano.dam.yourconcierge.initdata

import com.dsantano.dam.yourconcierge.entities.ComunityServices
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.repositories.ComunityServicesRepository
import com.dsantano.dam.yourconcierge.repositories.TicketRepository
import com.dsantano.dam.yourconcierge.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitDataComponent(
        val ticketRepository: TicketRepository,
        val comunityServicesRepository: ComunityServicesRepository,
        val userRepository: UserRepository,
        private val encoder: PasswordEncoder
) {

    @PostConstruct
    fun initData() {
        val userUser = MyUser("username", encoder.encode("123456"), "usernameFullName","USER", "1º", "A")
        val userAdmin = MyUser("admin", encoder.encode("123456"), "adminnameFullName","ADMIN", "2º", "C/D   ")
        userRepository.save(userUser)
        userRepository.save(userAdmin)

        val allTickets = listOf(
                Ticket("Bombilla rota", "Bombilla rota en el 3º piso", urgent = false, open = true, myUser = userUser),
                Ticket("Gotera", "Gotera en la entraba del edificio", urgent = true, open = true, myUser = userUser),
                Ticket("Compra de material", "EScalera de uso comunitario rota debemos comprar otra", urgent = true, open = true, myUser = userAdmin),
                Ticket("Recaudación", "REcaudación de alimentos solidaria para el día 3", urgent = false, open = true, myUser = userAdmin)

        )
        ticketRepository.saveAll(allTickets)

        val allCOmunityServices = listOf(
                ComunityServices(trashBags = true, cleanFloor = false, myUser = userUser),
                ComunityServices(trashBags = true, cleanFloor = false, myUser = userAdmin)

        )
        comunityServicesRepository.saveAll(allCOmunityServices)

    }
}