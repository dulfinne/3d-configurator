package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class WallRequest(
    val left: UUID,
    val right: UUID,
    val front: UUID,
    val back: UUID
)