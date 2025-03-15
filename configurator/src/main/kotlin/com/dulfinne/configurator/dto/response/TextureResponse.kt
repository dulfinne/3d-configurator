package com.dulfinne.configurator.dto.response

import java.util.UUID

data class TextureResponse(
    val id: UUID?,

    val name: String,

    val baseColor: String,

    val baseTextureUrl: String,

    val alphaMapUrl: String?,

    val bumpMapUrl: String?,

    val normalMapUrl: String?,

    val metalnessMapUrl: String?,

    val roughnessMapUrl: String?,

    val aoMapUrl: String?,

    val displacementMapUrl: String?,

    val properties: TexturePropertiesResponse,

    val icon: IconResponse,
)
