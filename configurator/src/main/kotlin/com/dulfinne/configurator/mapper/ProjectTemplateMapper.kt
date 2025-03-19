package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.ProjectTemplateRequest
import com.dulfinne.configurator.dto.request.UpdateProjectTemplateRequest
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.entity.ProjectTemplate

fun ProjectTemplate.toResponse(): ProjectTemplateResponse = ProjectTemplateResponse(
    id = id,
    name = name,
    configuration = configuration,
    previewImageUrl = previewImageUrl,
)

fun ProjectTemplateRequest.toEntity(): ProjectTemplate = ProjectTemplate(
    id = null,
    designProject = null,
    name = name,
    configuration = configuration,
    previewImageUrl = ""
)

fun ProjectTemplate.updateFromRequest(request: UpdateProjectTemplateRequest) {
    this.name = request.name
    this.configuration = request.configuration
}