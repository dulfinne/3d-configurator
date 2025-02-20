package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.TexturePropertiesRequest
import com.dulfinne.configurator.dto.response.TexturePropertiesResponse
import com.dulfinne.configurator.entity.TextureProperties

fun TextureProperties.toResponse(): TexturePropertiesResponse = TexturePropertiesResponse(
    id = id,
    bumpScale = bumpScale,
    metalness = metalness,
    roughness = roughness,
    emissiveIntensity = emissiveIntensity
)

fun TexturePropertiesRequest.toEntity(): TextureProperties = TextureProperties(
    id = null,
    bumpScale = bumpScale,
    metalness = metalness ?: 0.0,
    roughness = roughness ?: 0.0,
    emissiveIntensity = emissiveIntensity
)

fun TextureProperties.updateFromRequest(propertiesRequest: TexturePropertiesRequest) {
    this.bumpScale = propertiesRequest.bumpScale
    this.metalness = propertiesRequest.metalness ?: 0.0
    this.roughness = propertiesRequest.roughness ?: 0.0
    this.emissiveIntensity = propertiesRequest.emissiveIntensity
}