package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class ControlPanelRequest(
    val indicationBoard: UUID,
    val texture: UUID,
    val side: String,
    val location: String,
    val type: String,
)
