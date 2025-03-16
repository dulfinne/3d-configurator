package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.DesignProjectRequest
import com.dulfinne.configurator.dto.response.DesignProjectResponse
import com.dulfinne.configurator.entity.DesignProject

fun DesignProject.toResponse(): DesignProjectResponse = DesignProjectResponse(
    id = id,
    name = name,
)

fun DesignProjectRequest.toEntity(): DesignProject = DesignProject(
    id = null,
    name = name,
)

fun DesignProject.updateFromRequest(request: DesignProjectRequest) {
    this.name = request.name
}