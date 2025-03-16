package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.DesignProjectApi
import com.dulfinne.configurator.dto.request.DesignProjectRequest
import com.dulfinne.configurator.dto.response.DesignProjectResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.service.DesignProjectService
import com.dulfinne.configurator.service.ProjectTemplateService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/design-projects")
@CrossOrigin
class DesignProjectController(
    val designProjectService: DesignProjectService,
    val projectTemplateService: ProjectTemplateService
) : DesignProjectApi {

    @GetMapping
    override fun getAllDesignProjects(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<DesignProjectResponse> {
        val projects = designProjectService.getAllDesignProjects(page, size)
        return projects
    }

    @GetMapping("/templates/{designProjectId}")
    override fun getAllProjectTemplates(
        @PathVariable designProjectId: UUID,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<ProjectTemplateResponse> {
        val projects = projectTemplateService.getAllTemplatesByDesignProjectId(designProjectId, page, size)
        return projects
    }

    @GetMapping("/{projectId}")
    override fun getDesignProject(@PathVariable projectId: UUID): DesignProjectResponse {
        val project = designProjectService.getDesignProjectById(projectId)
        return project
    }

    @PostMapping
    override fun createDesignProject(@RequestBody @Valid request: DesignProjectRequest): ResponseEntity<DesignProjectResponse> {
        val project = designProjectService.createDesignProject(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(project)
    }

    @PutMapping("/{projectId}")
    override fun updateDesignProject(
        @PathVariable projectId: UUID,
        @RequestBody @Valid request: DesignProjectRequest
    ): DesignProjectResponse {
        val project = designProjectService.updateDesignProject(projectId, request)
        return project
    }

    @DeleteMapping("/{projectId}")
    override fun deleteDesignProject(@PathVariable projectId: UUID): ResponseEntity<Unit> {
        designProjectService.deleteDesignProjectById(projectId)
        return ResponseEntity.noContent().build()
    }
}