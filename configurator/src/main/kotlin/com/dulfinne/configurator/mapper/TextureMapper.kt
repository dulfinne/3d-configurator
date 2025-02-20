package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.entity.Texture

fun Texture.toResponse(): TextureResponse = TextureResponse(
    id = id,
    name = name,
    baseTextureUrl = baseTextureUrl,
    alphaMapUrl = alphaMapUrl,
    bumpMapUrl = bumpMapUrl,
    properties = properties.toResponse(),
    icon = icon.toResponse(),
)

fun TextureRequest.toEntity(): Texture = Texture(
    id = null,
    name = name,
    baseTextureUrl = baseTextureUrl,
    alphaMapUrl = alphaMapUrl,
    bumpMapUrl = bumpMapUrl,
    properties = properties.toEntity(),
    icon = icon.toEntity(),
)

fun Texture.updateFromRequest(updateRequest: TextureRequest) {
    this.name = updateRequest.name
    this.baseTextureUrl = updateRequest.baseTextureUrl
    this.alphaMapUrl = updateRequest.alphaMapUrl
    this.bumpMapUrl = updateRequest.bumpMapUrl
    this.properties.updateFromRequest(updateRequest.properties)
    this.icon.updateFromRequest(updateRequest.icon)
}



