package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.AuthApi
import com.dulfinne.configurator.dto.request.auth.LoginRequest
import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import com.dulfinne.configurator.dto.request.auth.RegistrationRequest
import com.dulfinne.configurator.service.AuthService
import jakarta.validation.Valid
import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(val authService: AuthService) : AuthApi {

    @PostMapping("/register")
    override fun register(@RequestBody request: @Valid RegistrationRequest): ResponseEntity<Unit> {
        authService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/login")
    override fun login(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<AccessTokenResponse> {
        return ResponseEntity.ok(authService.getJwt(loginRequest))
    }

    @PostMapping("/refresh-token")
    override fun refreshToken(
        @RequestBody request: @Valid RefreshTokenRequest
    ): ResponseEntity<AccessTokenResponse> {
        return ResponseEntity.ok(authService.refreshToken(request))
    }
}