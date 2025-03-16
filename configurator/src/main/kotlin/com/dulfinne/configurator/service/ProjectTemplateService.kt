package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ProjectTemplateRequest
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import java.util.UUID

interface ProjectTemplateService {
    fun getAllTemplatesByDesignProjectId(
        designProjectId: UUID,
        page: Int,
        size: Int
    ): PaginatedResponse<ProjectTemplateResponse>

    fun getProjectTemplateById(id: UUID): ProjectTemplateResponse
    fun createProjectTemplate(request: ProjectTemplateRequest): ProjectTemplateResponse
    fun updateProjectTemplate(id: UUID, request: ProjectTemplateRequest): ProjectTemplateResponse
    fun deleteProjectTemplateById(id: UUID)
}