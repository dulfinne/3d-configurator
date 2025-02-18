package com.dulfinne.configurator.dto.response

import java.util.UUID

data class TexturePropertiesResponse(
    val uuid: UUID?,

    val bumpScale: Double?,

    val metalness: Double,

    val roughness: Double,

    val emissiveIntensity: Double?,
)
