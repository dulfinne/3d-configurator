package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

data class UpdateProjectTemplateRequest(
    @field:NotNull(message = ValidationMessages.PROJECT_ID_REQUIRED)
    var designProjectId: UUID?,

    @field:NotBlank(message = ValidationMessages.NAME_REQUIRED)
    val name: String,

    @field:NotBlank(message = ValidationMessages.CONFIGURATION_REQUIRED)
    val configuration: String,

    val previewImage: MultipartFile?
)