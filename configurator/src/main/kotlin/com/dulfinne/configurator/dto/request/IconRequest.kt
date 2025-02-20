package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.entity.IconType
import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class IconRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

    @field:NotBlank(message = ValidationMessages.URL_REQUIRED)
    val url: String,

    @field:NotNull(message = ValidationMessages.TYPE_REQUIRED)
    val type: IconType?,
)
