package com.dulfinne.configurator.dto.request

import jakarta.validation.constraints.NotNull

data class TexturePropertiesRequest(
    val bumpScale: Double?,

    @NotNull(message = "Metalness can not be empty")
    val metalness: Double,

    @NotNull(message = "Roughness can not be empty")
    val roughness: Double,

    val emissiveIntensity: Double?,
)
