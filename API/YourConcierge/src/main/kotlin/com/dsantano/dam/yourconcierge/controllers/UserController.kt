package com.dsantano.dam.yourconcierge.controllers

import com.dsantano.dam.yourconcierge.dtos.CreateUserDTO
import com.dsantano.dam.yourconcierge.dtos.UserDTO
import com.dsantano.dam.yourconcierge.dtos.toUserDTO
import com.dsantano.dam.yourconcierge.entities.User
import com.dsantano.dam.yourconcierge.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/")
    fun newUser(@RequestBody newUser : CreateUserDTO): ResponseEntity<UserDTO> =
            userService.create(newUser).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUserDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "Username: ${newUser.username} already exists")
            }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    fun me(@AuthenticationPrincipal user : User) = user.toUserDTO()
}