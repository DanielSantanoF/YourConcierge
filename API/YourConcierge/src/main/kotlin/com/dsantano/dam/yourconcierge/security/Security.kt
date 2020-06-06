package com.dsantano.dam.yourconcierge.security

import com.dsantano.dam.yourconcierge.security.jsonwebtoken.JwtAuthenticationEntryPoint
import com.dsantano.dam.yourconcierge.security.jsonwebtoken.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigurePasswordEncoder() {

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

}

@Configuration
class ConfigureCors() {

    @Bean
    fun corsConfigurer()  = object : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                    .maxAge(3600)
        }
    }


}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(
        private val userDetailsService: UserDetailsService,
        private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        private val jwtAuthorizationFilter: JwtAuthorizationFilter,
        private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**", "/swagger**").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login", "/auth/token", "/user/register").permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/user/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/ticket/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/ticket/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/ticket/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/ticket/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/comunityservices/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/comunityservices/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/comunityservices/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/comunityservices/**").hasRole("USER")
                .anyRequest().hasRole("ADMIN")

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        //For the H2 console
        http.headers().frameOptions().disable()

        // @formatter:on
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


}
