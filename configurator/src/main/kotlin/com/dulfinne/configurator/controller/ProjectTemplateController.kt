package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.ProjectTemplateApi
import com.dulfinne.configurator.dto.request.ProjectTemplateRequest
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.service.ProjectTemplateService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/project-templates")
@CrossOrigin
class ProjectTemplateController(val projectTemplateService: ProjectTemplateService) : ProjectTemplateApi {

    @GetMapping("/{templateId}")
    override fun getProjectTemplate(@PathVariable templateId: UUID): ProjectTemplateResponse {
        val template = projectTemplateService.getProjectTemplateById(templateId)
        return template
    }

    @PostMapping
    override fun createProjectTemplate(@ModelAttribute @Valid request: ProjectTemplateRequest): ResponseEntity<ProjectTemplateResponse> {
        val template = projectTemplateService.createProjectTemplate(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(template)
    }

    @PutMapping("/{templateId}")
    override fun updateProjectTemplate(
        @PathVariable templateId: UUID,
        @ModelAttribute @Valid request: ProjectTemplateRequest
    ): ProjectTemplateResponse {
        val template = projectTemplateService.updateProjectTemplate(templateId, request)
        return template
    }

    @DeleteMapping("/{templateId}")
    override fun deleteProjectTemplate(@PathVariable templateId: UUID): ResponseEntity<Unit> {
        projectTemplateService.deleteProjectTemplateById(templateId)
        return ResponseEntity.noContent().build()
    }
}