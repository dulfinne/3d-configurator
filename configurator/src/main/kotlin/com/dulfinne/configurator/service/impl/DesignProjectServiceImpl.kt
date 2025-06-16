package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.DesignProjectRequest
import com.dulfinne.configurator.dto.response.DesignProjectResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.entity.DesignProject
import com.dulfinne.configurator.entity.ProjectTemplate
import com.dulfinne.configurator.mapper.toEntity
import com.dulfinne.configurator.mapper.toPaginatedResponse
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.mapper.updateFromRequest
import com.dulfinne.configurator.repository.DesignProjectRepository
import com.dulfinne.configurator.service.DesignProjectService
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.ExceptionMessages
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DesignProjectServiceImpl(val designProjectRepository: DesignProjectRepository, val imageService: ImageService) :
    DesignProjectService {

    @Transactional(readOnly = true)
    override fun getAllDesignProjects(page: Int, size: Int): PaginatedResponse<DesignProjectResponse> {
        val designProjects = designProjectRepository.findAll(PageRequest.of(page, size))
        return designProjects.map { it.toResponse() }.toPaginatedResponse()
    }

    @Transactional(readOnly = true)
    override fun getDesignProjectById(id: UUID): DesignProjectResponse {
        val project = getDesignProjectIfExists(id)
        return project.toResponse()
    }

    private fun getDesignProjectIfExists(id: UUID): DesignProject {
        return designProjectRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ExceptionMessages.DESIGN_PROJECT_NOT_FOUND_ID.format(id))

    }

    @Transactional
    override fun createDesignProject(request: DesignProjectRequest): DesignProjectResponse {
        val designProject = request.toEntity()
        checkNameUniqueness(designProject.name)
        designProjectRepository.save(designProject)
        return designProject.toResponse()
    }

    @Transactional
    override fun updateDesignProject(id: UUID, request: DesignProjectRequest): DesignProjectResponse {
        val designProject = getDesignProjectIfExists(id)
        checkNameUniqueness(request.name, designProject.name)
        designProject.updateFromRequest(request)
        return designProject.toResponse()
    }

    @Transactional
    override fun deleteDesignProjectById(id: UUID) {
        val designProject = getDesignProjectIfExists(id)
        designProjectRepository.delete(designProject)
        deleteAllTemplatesPreviewImages(designProject.templates)
    }

    private fun deleteAllTemplatesPreviewImages(templates: List<ProjectTemplate>) {
        for (template in templates) {
            imageService.deleteImage(BucketNames.PROJECT_PREVIEW_IMAGE_BUCKET, template.name)
        }
    }



    private fun checkNameUniqueness(name: String) {
        designProjectRepository.findByName(name)?.let {
            throw EntityExistsException(ExceptionMessages.DESIGN_PROJECT_EXISTS_NAME.format(name))
        }
    }

    private fun checkNameUniqueness(newName: String, currentName: String) {
        if (!newName.equals(currentName, ignoreCase = true)) {
            checkNameUniqueness(newName)
        }
    }
}