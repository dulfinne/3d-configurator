package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class BumpersRequest(
    val left: UUID,
    val right: UUID,
    val back: UUID
)
