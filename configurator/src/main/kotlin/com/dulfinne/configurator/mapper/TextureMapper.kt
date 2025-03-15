package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.entity.Texture

fun Texture.toResponse(): TextureResponse = TextureResponse(
    id = id,
    name = name,
    baseColor = baseColor,
    baseTextureUrl = baseTextureUrl,
    alphaMapUrl = alphaMapUrl,
    bumpMapUrl = bumpMapUrl,
    normalMapUrl = normalMapUrl,
    metalnessMapUrl = metalnessMapUrl,
    roughnessMapUrl = roughnessMapUrl,
    aoMapUrl = aoMapUrl,
    displacementMapUrl = displacementMapUrl,
    properties = properties.toResponse(),
    icon = icon.toResponse(),
)

fun TextureRequest.toEntity(): Texture = Texture(
    id = null,
    name = name,
    baseColor = baseColor,
    baseTextureUrl = "",
    alphaMapUrl = null,
    bumpMapUrl = null,
    normalMapUrl = null,
    metalnessMapUrl = null,
    roughnessMapUrl = null,
    aoMapUrl = null,
    displacementMapUrl = null,
    properties = properties.toEntity(),
    icon = icon.toEntity(),
)

fun Texture.updateFromRequest(updateRequest: TextureRequest) {
    this.name = updateRequest.name
    this.baseColor = updateRequest.baseColor
    this.baseTextureUrl = ""
    this.alphaMapUrl = null
    this.bumpMapUrl = null
    this.normalMapUrl = null
    this.metalnessMapUrl = null
    this.roughnessMapUrl = null
    this.aoMapUrl = null
    this.displacementMapUrl = null
    this.properties.updateFromRequest(updateRequest.properties)
    this.icon.updateFromRequest(updateRequest.icon)
}



