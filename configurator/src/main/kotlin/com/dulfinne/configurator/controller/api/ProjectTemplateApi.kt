package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.ProjectTemplateRequest
import com.dulfinne.configurator.dto.request.UpdateProjectTemplateRequest
import com.dulfinne.configurator.dto.response.ProjectTemplateResponse
import com.dulfinne.configurator.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "Project template Controller", description = "Managing templates of design projects")
interface ProjectTemplateApi {

    @Operation(
        summary = "Get template by ID",
        description = "Returns a specific template by its unique ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved template",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ProjectTemplateResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Template not found",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/{templateId}")
    fun getProjectTemplate(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable templateId: UUID
    ): ProjectTemplateResponse


    @Operation(
        summary = "Create a new template",
        description = "Creates a new template based on the provided request"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Successfully created template",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ProjectTemplateResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Template already exists cause of same parameters",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid parameters",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Design project not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PostMapping
    fun createProjectTemplate(@RequestBody @Valid request: ProjectTemplateRequest): ResponseEntity<ProjectTemplateResponse>

    @Operation(
        summary = "Update template by ID",
        description = "Updates the template information for a given template ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully updated template",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ProjectTemplateResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Template already exists cause of same parameters",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid parameters",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @PutMapping("/{templateId}")
    fun updateProjectTemplate(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable templateId: UUID,
        @RequestBody @Valid request: UpdateProjectTemplateRequest
    ): ProjectTemplateResponse

    @Operation(
        summary = "Delete template by ID",
        description = "Deletes the template associated with the given ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "Successfully deleted template"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Template not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @DeleteMapping("/{templateId}")
    fun deleteProjectTemplate(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable templateId: UUID
    ): ResponseEntity<Unit>
}