package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.DesignProjectRequest
import com.dulfinne.configurator.dto.response.DesignProjectResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import java.util.UUID

interface DesignProjectService {
    fun getAllDesignProjects(page: Int, size: Int): PaginatedResponse<DesignProjectResponse>
    fun getDesignProjectById(id: UUID): DesignProjectResponse
    fun createDesignProject(request: DesignProjectRequest): DesignProjectResponse
    fun updateDesignProject(id: UUID, request: DesignProjectRequest): DesignProjectResponse
    fun deleteDesignProjectById(id: UUID)
}