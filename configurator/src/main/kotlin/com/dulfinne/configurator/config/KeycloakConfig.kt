package com.dulfinne.configurator.config

import com.dulfinne.configurator.props.KeycloakProperties
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfig(val properties: KeycloakProperties) {

    @Bean
    fun keycloak(): Keycloak = KeycloakBuilder.builder()
        .serverUrl(properties.serverUrl)
        .realm(properties.realm)
        .clientId(properties.client.id)
        .clientSecret(properties.client.secret)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS).build()

    @Bean
    fun realmResource(keycloak: Keycloak): RealmResource =
        keycloak.realm(properties.realm)
}