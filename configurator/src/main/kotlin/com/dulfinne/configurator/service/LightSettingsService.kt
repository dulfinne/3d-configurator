package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.LightSettingsRequest
import com.dulfinne.configurator.dto.response.LightSettingsResponse

interface LightSettingsService {
    fun getLightSettings(): LightSettingsResponse
    fun updateLightSettings(request: LightSettingsRequest): LightSettingsResponse
    fun saveLightSettings(request: LightSettingsRequest): LightSettingsResponse
}