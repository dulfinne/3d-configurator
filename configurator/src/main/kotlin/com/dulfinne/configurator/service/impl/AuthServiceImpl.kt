package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.client.service.ClientService
import com.dulfinne.configurator.dto.request.auth.LoginRequest
import com.dulfinne.configurator.dto.request.auth.RefreshTokenRequest
import com.dulfinne.configurator.dto.request.auth.RegistrationRequest
import com.dulfinne.configurator.props.KeycloakProperties
import com.dulfinne.configurator.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.ws.rs.core.Response

@Service
class AuthServiceImpl(
    val clientService: ClientService,
    val realmResource: RealmResource,
    val properties: KeycloakProperties
) : AuthService {

    override fun refreshToken(request: RefreshTokenRequest): AccessTokenResponse {
        return clientService.refreshToken(request)
    }

    override fun createUser(request: RegistrationRequest) {
        val userRepresentation = setUserRepresentation(request)

        realmResource.users().create(userRepresentation).use { response ->
            checkResponseIsCreated(response)
        }
    }

    override fun getJwt(request: LoginRequest): AccessTokenResponse {
        KeycloakBuilder.builder()
            .serverUrl(properties.serverUrl)
            .realm(properties.realm)
            .grantType(OAuth2Constants.PASSWORD)
            .clientId(properties.client.id)
            .clientSecret(properties.client.secret)
            .username(request.username)
            .password(request.password)
            .build()
            .use { keycloak ->
                return keycloak.tokenManager().accessToken
            }
    }

    private fun checkResponseIsCreated(response: Response) {
        val status = response.status

        if (status != HttpStatus.CREATED.value()) {
            var message = response.statusInfo.reasonPhrase

            val responseBody = response.readEntity(String::class.java)
            val jsonNode = ObjectMapper().readTree(responseBody)
            message = jsonNode.path("errorMessage").asText(message)

            throw ResponseStatusException(HttpStatus.valueOf(status), message)
        }
    }

    private fun setUserRepresentation(request: RegistrationRequest): UserRepresentation {
        val userRepresentation = UserRepresentation().apply {
            isEnabled = true
            username = request.username
            firstName = request.firstName
            lastName = request.lastName
            isEmailVerified = true
        }

        val credentialRepresentation = CredentialRepresentation().apply {
            value = request.password
            type = CredentialRepresentation.PASSWORD
        }

        userRepresentation.credentials = listOf(credentialRepresentation)
        return userRepresentation
    }
}