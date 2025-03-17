package com.dulfinne.configurator.dto.request

import com.dulfinne.configurator.util.ValidationMessages
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class FileRequest(
    @field:NotNull(message = ValidationMessages.FILE_REQUIRED)
    val file: MultipartFile?,
)
