package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.entity.Texture

fun Texture.toResponse(): TextureResponse = TextureResponse(
    uuid = uuid!!,
    name = name,
    baseTextureUrl = baseTextureUrl,
    alphaMapUrl = alphaMapUrl,
    bumpMapUrl = bumpMapUrl,
    properties = properties.toResponse()
)

fun TextureRequest.toEntity(): Texture = Texture(
    uuid = null,
    name = name,
    baseTextureUrl = baseTextureUrl,
    alphaMapUrl = alphaMapUrl,
    bumpMapUrl = bumpMapUrl,
    properties = properties.toEntity()
)

fun Texture.updateFromRequest(updateRequest: TextureRequest) {
    this.name = updateRequest.name
    this.baseTextureUrl = updateRequest.baseTextureUrl
    this.alphaMapUrl = updateRequest.alphaMapUrl
    this.bumpMapUrl = updateRequest.bumpMapUrl
    this.properties.updateFromRequest(updateRequest.properties)
}



