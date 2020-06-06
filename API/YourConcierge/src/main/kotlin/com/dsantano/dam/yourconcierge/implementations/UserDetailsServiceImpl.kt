package com.dsantano.dam.yourconcierge.implementations

import com.dsantano.dam.yourconcierge.services.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service("userDetailsService")
class UserDetailsServiceImpl(
        private val userService: UserService
) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails =
            userService.findByUsername(username).orElseThrow {
                UsernameNotFoundException("User $username not found")
            }
}