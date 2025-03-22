package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.DesignProjectRequest
import com.dulfinne.configurator.dto.response.DesignProjectResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
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
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Tag(name = "Design project Controller", description = "Managing design projects")
interface DesignProjectApi {

    @Operation(
        summary = "Get all design projects with pagination",
        description = "Returns a paginated list of all design projects"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all design projects",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PaginatedResponse::class)
            )]
        )
    )
    @GetMapping
    fun getAllDesignProjects(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<DesignProjectResponse>

    @Operation(
        summary = "Get all templates with pagination",
        description = "Returns a paginated list of all templates"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all templates",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PaginatedResponse::class)
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
    @GetMapping("/{projectId}/templates")
    fun getAllProjectTemplates(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable projectId: UUID,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<ProjectTemplateResponse>

    @Operation(
        summary = "Get design project by ID",
        description = "Returns a specific design project by its unique ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved design project",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = DesignProjectResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Design project not found",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/{projectId}")
    fun getDesignProject(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable projectId: UUID
    ): DesignProjectResponse

    @Operation(
        summary = "Create a new design project",
        description = "Creates a new design project based on the provided request"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Successfully created design project",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = DesignProjectResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Design project already exists cause of same parameters",
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
    @PostMapping
    fun createDesignProject(@RequestBody @Valid request: DesignProjectRequest): ResponseEntity<DesignProjectResponse>

    @Operation(
        summary = "Update design project by ID",
        description = "Updates the design project for a given design project ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully updated design project",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = DesignProjectResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Design project not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Design project already exists cause of same parameters",
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
    @PutMapping("/{projectId}")
    fun updateDesignProject(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable projectId: UUID,
        @RequestBody @Valid request: DesignProjectRequest
    ): DesignProjectResponse

    @Operation(
        summary = "Delete design project by ID",
        description = "Deletes the design project associated with the given ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "Successfully deleted design project"
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
    @DeleteMapping("/{projectId}")
    fun deleteDesignProject(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable projectId: UUID
    ): ResponseEntity<Unit>
}