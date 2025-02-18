package com.dulfinne.configurator.dto.request

import jakarta.validation.constraints.NotBlank

data class TextureRequest(

    @NotBlank(message = "Name can not be blank")
    val name: String,

    @NotBlank(message = "Base texture url can not be blank")
    val baseTextureUrl: String,

    val alphaMapUrl: String?,

    val bumpMapUrl: String?,

    val properties: TexturePropertiesRequest
)
