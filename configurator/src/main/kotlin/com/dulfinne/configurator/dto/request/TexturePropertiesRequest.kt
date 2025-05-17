package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotNull

data class TexturePropertiesRequest(
    val bumpScale: Double?,

    @field:NotNull(message = ValidationMessages.METALNESS_REQUIRED)
    val metalness: Double?,

    @field:NotNull(message = ValidationMessages.ROUGHNESS_REQUIRED)
    val roughness: Double?,

    val emissiveIntensity: Double?,

    val tileSizeX: Double?,

    val tileSizeY: Double?,
)
