package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class TextureRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

   @field:NotNull(message = ValidationMessages.URL_REQUIRED)
    val baseTexture: MultipartFile,

    val alphaMap: MultipartFile?,

    val bumpMap: MultipartFile?,

    @field:Valid
    val properties: TexturePropertiesRequest,

    @field:Valid
    val icon: IconRequest
)
