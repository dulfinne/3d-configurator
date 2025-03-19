package com.dulfinne.configurator.client.service

import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import org.keycloak.representations.AccessTokenResponse

interface ClientService {
    fun refreshToken(request: RefreshTokenRequest): AccessTokenResponse
}