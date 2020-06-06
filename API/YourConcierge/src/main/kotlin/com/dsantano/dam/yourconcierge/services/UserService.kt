package com.dsantano.dam.yourconcierge.services

import com.dsantano.dam.yourconcierge.dtos.CreateUserDTO
import com.dsantano.dam.yourconcierge.entities.MyUser
import com.dsantano.dam.yourconcierge.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val repo: UserRepository,
        private val encoder: PasswordEncoder
) {

    fun create(myUser : MyUser): Optional<MyUser> {
        if (findByUsername(myUser.username).isPresent)
            return Optional.empty()
        return Optional.of(
                repo.save(myUser.copy(
                        password = encoder.encode(myUser.password)
                ))
        )
    }

    fun create(newUser : CreateUserDTO): Optional<MyUser> {
        if (findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(
                with(newUser) {
                    repo.save(MyUser(username, encoder.encode(password), fullName, floor, number, "USER"))
                }

        )
    }

    fun findByUsername(username : String) = repo.findByUsername(username)

    fun findById(id : UUID) = repo.findById(id)
}