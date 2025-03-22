package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.ProjectTemplateRequest
import com.dulfinne.configurator.dto.request.UpdateProjectTemplateRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.entity.DesignProject
import com.dulfinne.configurator.entity.ProjectTemplate
import com.dulfinne.configurator.mapper.toEntity
import com.dulfinne.configurator.mapper.toPaginatedResponse
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.mapper.updateFromRequest
import com.dulfinne.configurator.repository.DesignProjectRepository
import com.dulfinne.configurator.repository.ProjectTemplateRepository
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.service.ProjectTemplateService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.ExceptionMessages
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class ProjectTemplateServiceImpl(
    val projectTemplateRepository: ProjectTemplateRepository,
    val designProjectRepository: DesignProjectRepository,
    val imageService: ImageService
) : ProjectTemplateService {

    @Transactional(readOnly = true)
    override fun getAllTemplatesByDesignProjectId(
        designProjectId: UUID,
        page: Int,
        size: Int
    ): PaginatedResponse<ProjectTemplateResponse> {
        getDesignProjectIfExists(designProjectId)
        val templates = projectTemplateRepository.findAllByDesignProjectId(designProjectId, PageRequest.of(page, size))
        return templates.map { it.toResponse() }.toPaginatedResponse()
    }

    @Transactional(readOnly = true)
    override fun getProjectTemplateById(id: UUID): ProjectTemplateResponse {
        val projectTemplate = getProjectTemplateIfExists(id)
        return projectTemplate.toResponse()
    }

    @Transactional
    override fun createProjectTemplate(request: ProjectTemplateRequest): ProjectTemplateResponse {
        checkNameUniqueness(request.name)
        val projectTemplate = request.toEntity()

        val designProject = getDesignProjectIfExists(request.designProjectId ?: UUID.randomUUID())
        projectTemplate.designProject = designProject

        uploadPreviewImageIfPresent(projectTemplate, request.previewImage)

        projectTemplateRepository.save(projectTemplate)
        return projectTemplate.toResponse()
    }

    @Transactional
    override fun updateProjectTemplate(id: UUID, request: UpdateProjectTemplateRequest): ProjectTemplateResponse {
        val projectTemplate = getProjectTemplateIfExists(id)

        val oldName = projectTemplate.name
        val newName = request.name
        val nameChanged = oldName != newName
        if (nameChanged) {
            handleNameChange(projectTemplate, request.name)
        }

        projectTemplate.updateFromRequest(request)
        uploadPreviewImageIfPresent(projectTemplate, request.previewImage)

        request.designProjectId?.let { projectTemplate.designProject = getDesignProjectIfExists(it) }

        projectTemplateRepository.save(projectTemplate)
        return projectTemplate.toResponse()
    }

    @Transactional
    override fun deleteProjectTemplateById(id: UUID) {
        val projectTemplate = getProjectTemplateIfExists(id)
        projectTemplateRepository.delete(projectTemplate)
        imageService.deleteImage(BucketNames.PROJECT_PREVIEW_IMAGE_BUCKET, projectTemplate.name)
    }

    private fun handleNameChange(projectTemplate: ProjectTemplate, newName: String) {
        checkNameUniqueness(newName)
        val url = imageService.renameFile(
            BucketNames.PROJECT_PREVIEW_IMAGE_BUCKET, newName, projectTemplate.name, projectTemplate.previewImageUrl
        )
        url?.let { projectTemplate.previewImageUrl = it }
    }

    private fun uploadPreviewImageIfPresent(projectTemplate: ProjectTemplate, image: MultipartFile?) {
        image?.let {
            projectTemplate.previewImageUrl = imageService.uploadImage(
                BucketNames.PROJECT_PREVIEW_IMAGE_BUCKET, projectTemplate.name, it
            )
        }
    }

    private fun getDesignProjectIfExists(id: UUID): DesignProject {
        return designProjectRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ExceptionMessages.DESIGN_PROJECT_NOT_FOUND_ID.format(id))

    }

    private fun getProjectTemplateIfExists(id: UUID): ProjectTemplate {
        return projectTemplateRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ExceptionMessages.PROJECT_TEMPLATE_NOT_FOUND_ID.format(id))
    }

    private fun checkNameUniqueness(name: String) {
        projectTemplateRepository.findByName(name)?.let {
            throw EntityExistsException(ExceptionMessages.PROJECT_TEMPLATE_EXISTS_NAME.format(name))
        }
    }
}