package com.dulfinne.configurator.config

import com.dulfinne.configurator.props.KeycloakProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(val keycloakProperties: KeycloakProperties) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/docs/**")
                    .permitAll()
                    .requestMatchers(
                        "/api/v1/auth/login",
                        "/api/v1/elevators/documents/**",
                    )
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/design-projects/**",
                        "/api/v1/icons/**",
                        "/api/v1/project-templates/**",
                        "/api/v1/textures/**"
                    )
                    .permitAll()
                    .requestMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwkSetUri("${keycloakProperties.serverUrl}/realms/${keycloakProperties.realm}/protocol/openid-connect/certs")
                }
            }
        return http.build()
    }
}