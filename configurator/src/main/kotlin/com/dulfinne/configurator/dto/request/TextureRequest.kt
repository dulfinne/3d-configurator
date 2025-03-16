package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile

data class TextureRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

    @field:NotBlank(message = ValidationMessages.COLOR_REQUIRED)
    val baseColor: String,

    val baseTexture: MultipartFile?,

    val alphaMap: MultipartFile?,

    val bumpMap: MultipartFile?,

    val normalMap: MultipartFile?,

    val metalnessMap: MultipartFile?,

    val roughnessMap: MultipartFile?,

    val aoMap: MultipartFile?,

    val displacementMap: MultipartFile?,

    @field:Valid
    val properties: TexturePropertiesRequest,

    @field:Valid
    val icon: IconRequest
)
