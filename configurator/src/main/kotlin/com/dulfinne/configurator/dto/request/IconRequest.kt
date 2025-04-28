package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class IconRequest(

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

    @field:NotNull(message = ValidationMessages.FILE_REQUIRED)
    val icon: MultipartFile?,

    val isDoor: Boolean = false,

    val isWall: Boolean = false,

    val isFloor: Boolean = false,

    val isCeiling: Boolean = false,

    val isCeilingMaterial: Boolean = false,

    val isControlPanel: Boolean = false,

    val isHandrail: Boolean = false,

    val isBumper: Boolean = false,

    val isIndicationBoard: Boolean = false,

    val isFrame: Boolean = false
)
