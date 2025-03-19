package com.dulfinne.configurator.dto.request.auth

data class RegistrationRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)
