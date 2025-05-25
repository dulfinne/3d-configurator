package com.dulfinne.configurator.dto.response

data class LightSettingsResponse (
    val reactIntensity: Double,

    val reactColor: String,

    val ambientIntensity: Double,

    val ambientColor: String,
)