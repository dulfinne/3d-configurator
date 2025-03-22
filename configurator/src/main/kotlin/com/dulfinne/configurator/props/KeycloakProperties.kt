package com.dulfinne.configurator.props

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "keycloak")
class KeycloakProperties {
    lateinit var realm: String
    lateinit var serverUrl: String
    var client: Client = Client()

    class Client {
        lateinit var id: String
        lateinit var secret: String
    }
}
