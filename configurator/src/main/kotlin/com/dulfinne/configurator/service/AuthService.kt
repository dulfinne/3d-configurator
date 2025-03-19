package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.auth.LoginRequest
import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import com.dulfinne.configurator.dto.request.auth.RegistrationRequest
import org.keycloak.representations.AccessTokenResponse

interface AuthService {
    fun createUser(request: RegistrationRequest)

    fun getJwt(request: LoginRequest): AccessTokenResponse

    fun refreshToken(request: RefreshTokenRequest): AccessTokenResponse
}