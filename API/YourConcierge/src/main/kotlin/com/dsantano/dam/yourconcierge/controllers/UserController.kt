package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.dtos.*
import com.dsantano.dam.yourconcierge.entities.Ticket
import com.dsantano.dam.yourconcierge.entities.User
import com.dsantano.dam.yourconcierge.repositories.UserRepository
import com.dsantano.dam.yourconcierge.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Controller
@RequestMapping("/user")
class UserController(
        val userService: UserService,
        val repo: UserRepository,
        private val encoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun newUser(@RequestBody newUser : CreateUserDTO): ResponseEntity<UserDTO> =
            userService.create(newUser).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUserDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "Username: ${newUser.username} already exists")
            }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user : User) = user.toUserDTO()

    @PutMapping("/{id}")
    fun updateUser(@RequestBody updateUser: User, @PathVariable id : UUID) : UserDTO {
        return repo.findById( id ).map { it ->
            val userUpdated : User = it.copy( username = updateUser.username, password = encoder.encode(updateUser.password), fullName = updateUser.fullName)
            repo.save(userUpdated).toUserDTO()
        }.orElseThrow() {
            ResponseStatusException( HttpStatus.NOT_FOUND, "Not results found" )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id : UUID) : ResponseEntity<Void> {
        repo.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}