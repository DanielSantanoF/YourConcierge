package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.dtos.*
import com.dsantano.dam.yourconcierge.entities.MyUser
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

    @PutMapping("/{id}")
    fun updateUser(@RequestBody updateMyUser: MyUser, @PathVariable id : UUID) : UserDTO {
        return repo.findById( id ).map { it ->
            val myUserUpdated : MyUser = it.copy( username = updateMyUser.username, password = encoder.encode(updateMyUser.password), fullName = updateMyUser.fullName)
            repo.save(myUserUpdated).toUserDTO()
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