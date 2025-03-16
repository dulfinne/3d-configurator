package com.dulfinne.configurator.dto.response

import java.util.UUID

data class ProjectTemplateResponse(
    val id: UUID?,

    val name: String,

    val configuration: String,

    val previewImageUrl: String
)