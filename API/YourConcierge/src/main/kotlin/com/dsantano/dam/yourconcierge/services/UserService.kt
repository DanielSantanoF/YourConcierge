package com.dsantano.dam.yourconcierge.services

import com.dsantano.dam.yourconcierge.dtos.CreateUserDTO
import com.dsantano.dam.yourconcierge.entities.User
import com.dsantano.dam.yourconcierge.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
        private val repo: UserRepository,
        private val encoder: PasswordEncoder
) {

    fun create(user : User): Optional<User> {
        if (findByUsername(user.username).isPresent)
            return Optional.empty()
        return Optional.of(
                repo.save(user.copy(
                        password = encoder.encode(user.password)
                ))
        )
    }

    fun create(newUser : CreateUserDTO): Optional<User> {
        if (findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(
                with(newUser) {
                    repo.save(User(username, encoder.encode(password), fullName, "USER"))
                }

        )
    }

    fun findByUsername(username : String) = repo.findByUsername(username)

    fun findById(id : UUID) = repo.findById(id)
}