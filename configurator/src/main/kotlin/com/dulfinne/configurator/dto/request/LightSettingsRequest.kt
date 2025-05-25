package com.dulfinne.configurator.dto.request

data class LightSettingsRequest(
    val reactIntensity: Double,

    val reactColor: String,

    val ambientIntensity: Double,

    val ambientColor: String,
)
