package com.dsantano.dam.yourconcierge

import com.dsantano.dam.yourconcierge.entities.User
import com.dsantano.dam.yourconcierge.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class YourConciergeApplication {
	private val logger = LoggerFactory.getLogger(javaClass)

	@Bean
	fun setupInitialData(
			userService: UserService
	) = CommandLineRunner {
		_ ->
		run {
			var user1 = User(username = "user", password = "123456", fullName = "FullnameUser", roles = mutableSetOf("USER"))
			var user2 = User(username = "admin", password = "123456", fullName = "FullnameAdmin", roles = mutableSetOf("ADMIN"))


			user1 = userService.create(user1).orElseThrow()
			user2 = userService.create(user2).orElseThrow()

			logger.info(user1.toString())
			logger.info(user2.toString())


		}
	}
}

fun main(args: Array<String>) {
	runApplication<YourConciergeApplication>(*args)
}
