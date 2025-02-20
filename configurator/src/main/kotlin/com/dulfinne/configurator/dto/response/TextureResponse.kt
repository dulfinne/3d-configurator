package com.dulfinne.configurator.dto.response

import java.util.UUID

data class TextureResponse(
    val id: UUID?,

    val name: String,

    val baseTextureUrl: String,

    val alphaMapUrl: String?,

    val bumpMapUrl: String?,

    val properties: TexturePropertiesResponse,
    val icon: IconResponse,
)
