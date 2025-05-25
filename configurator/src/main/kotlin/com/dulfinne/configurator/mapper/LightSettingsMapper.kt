package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.LightSettingsRequest
import com.dulfinne.configurator.dto.response.LightSettingsResponse
import com.dulfinne.configurator.entity.LightSettings

fun LightSettings.toResponse(): LightSettingsResponse = LightSettingsResponse(
    reactIntensity = this.reactIntensity,
    reactColor = this.reactColor,
    ambientIntensity = this.ambientIntensity,
    ambientColor = this.ambientColor,
)

fun LightSettingsRequest.toEntity(): LightSettings = LightSettings(
    id = 1L,
    reactIntensity = reactIntensity,
    reactColor = reactColor,
    ambientIntensity = ambientIntensity,
    ambientColor = ambientColor,
)

fun LightSettings.updateFromRequest(request: LightSettingsRequest) {
    reactColor = request.reactColor
    reactIntensity = request.reactIntensity
    ambientIntensity = request.ambientIntensity
    ambientColor = request.ambientColor
}