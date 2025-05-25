package com.dulfinne.configurator.dto.request.configuration

import java.util.UUID

data class CabinRequest(
    val size: String,
    val type: String,
    val openingType: String,
    val designProject: UUID,
)
