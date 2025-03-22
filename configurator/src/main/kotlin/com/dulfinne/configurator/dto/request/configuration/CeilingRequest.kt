package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class CeilingRequest(
    val lamp: UUID,
    val texture: UUID,
)
