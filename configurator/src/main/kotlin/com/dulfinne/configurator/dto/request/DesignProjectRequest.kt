package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotBlank

data class DesignProjectRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,
)
