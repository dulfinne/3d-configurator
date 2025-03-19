package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.auth.LoginRequest
import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import com.dulfinne.configurator.dto.request.auth.RegistrationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Auth Controller", description = "Work with authentication")
interface AuthApi {
    @Operation(
        operationId = "registerUser",
        summary = "Register user for not admin roles",
        responses = [ApiResponse(responseCode = "201"), ApiResponse(
            responseCode = "403",
            description = "Attempt to create admin",
        )]
    )
    @PostMapping("/register")
    fun register(@RequestBody request: @Valid RegistrationRequest): ResponseEntity<Unit>

    @Operation(
        operationId = "login",
        summary = "Login and get jwt's",
        responses = [ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AccessTokenResponse::class)
                )
            ]
        )]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: @Valid LoginRequest): ResponseEntity<AccessTokenResponse>

    @Operation(
        operationId = "refreshToken",
        summary = "Refreshes your token",
        responses = [ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AccessTokenResponse::class)
                )
            ]
        )]
    )
    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: @Valid RefreshTokenRequest): ResponseEntity<AccessTokenResponse>
}