package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.TexturePropertiesRequest
import com.dulfinne.configurator.dto.response.TexturePropertiesResponse
import com.dulfinne.configurator.entity.TextureProperties

fun TextureProperties.toResponse(): TexturePropertiesResponse = TexturePropertiesResponse(
    uuid = uuid,
    bumpScale = bumpScale,
    metalness = metalness,
    roughness = roughness,
    emissiveIntensity = emissiveIntensity
)

fun TexturePropertiesRequest.toEntity(): TextureProperties = TextureProperties(
    uuid = null,
    bumpScale = bumpScale,
    metalness = metalness,
    roughness = roughness,
    emissiveIntensity = emissiveIntensity
)

fun TextureProperties.updateFromRequest(propertiesRequest: TexturePropertiesRequest) {
    this.bumpScale = propertiesRequest.bumpScale
    this.metalness = propertiesRequest.metalness
    this.roughness = propertiesRequest.roughness
    this.emissiveIntensity = propertiesRequest.emissiveIntensity
}