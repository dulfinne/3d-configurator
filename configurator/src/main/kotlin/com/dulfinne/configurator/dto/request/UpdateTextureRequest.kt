package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.multipart.MultipartFile

data class UpdateTextureRequest(

    @field:NotBlank(message = ValidationMessages.COLOR_REQUIRED)
    val baseColor: String,

    val baseTexture: MultipartFile?,
    val removeBaseTexture: Boolean = false,

    val alphaMap: MultipartFile?,
    val removeAlphaMap: Boolean = false,

    val bumpMap: MultipartFile?,
    val removeBumpMap: Boolean = false,

    val normalMap: MultipartFile?,
    val removeNormalMap: Boolean = false,

    val metalnessMap: MultipartFile?,
    val removeMetalnessMap: Boolean = false,

    val roughnessMap: MultipartFile?,
    val removeRoughnessMap: Boolean = false,

    val aoMap: MultipartFile?,
    val removeAoMap: Boolean = false,

    val displacementMap: MultipartFile?,
    val removeDisplacementMap: Boolean = false,

    @field:Valid
    val properties: TexturePropertiesRequest,

    @field:Valid
    val icon: UpdateIconRequest
)
