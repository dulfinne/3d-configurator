package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class HallRequest(
    val frameExistence: String,
    val frameTexture: UUID,
    val callPostType: String,
    val indicationBoardType: String,
)
