package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class HandrailRequest(
    val existence: String,
    val back: Boolean,
    val left: Boolean,
    val right: Boolean,
    val type: String,
    val texture: UUID
)
