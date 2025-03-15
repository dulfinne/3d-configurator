package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.entity.IconType
import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class IconRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

    @field:NotNull(message = ValidationMessages.FILE_REQUIRED)
    val icon: MultipartFile,

    @field:NotNull(message = ValidationMessages.TYPE_REQUIRED)
    val type: IconType?,
)
