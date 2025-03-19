package com.dulfinne.configurator.client.service.impl

import com.dulfinne.configurator.client.KeycloakClient
import com.dulfinne.configurator.client.service.ClientService
import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import com.dulfinne.configurator.props.KeycloakProperties
import org.keycloak.OAuth2Constants
import org.keycloak.representations.AccessTokenResponse
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl(val keycloakClient: KeycloakClient, val properties: KeycloakProperties) : ClientService {
    override fun refreshToken(request: RefreshTokenRequest): AccessTokenResponse {
        val token = request.refreshToken

        val clientRequest = mapOf(
            OAuth2Constants.GRANT_TYPE to OAuth2Constants.REFRESH_TOKEN,
            OAuth2Constants.CLIENT_ID to properties.client.id,
            OAuth2Constants.CLIENT_SECRET to properties.client.secret,
            OAuth2Constants.REFRESH_TOKEN to token
        )

        return keycloakClient.refreshToken(clientRequest)
    }
}