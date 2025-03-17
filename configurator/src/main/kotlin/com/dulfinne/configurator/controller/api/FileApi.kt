package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.FileRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "File Controller", description = "Managing configuration files")
interface FileApi {

    @Operation(
        operationId = "save3DModel",
        summary = "Save 3d-model to cloud storage"
    )
    @ApiResponses(
        ApiResponse(
            description = "Successfully uploaded 3d-model",
            responseCode = "201",
            content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)]
        )
    )
    @PostMapping("/3d-model")
    fun save3DModel(@RequestBody request: FileRequest): ResponseEntity<Unit>

    @Operation(
        operationId = "saveTemplateDocument",
        summary = "Upload document template to cloud-storage"
    )
    @ApiResponses(
        ApiResponse(
            description = "Successfully uploaded document template",
            responseCode = "200",
            content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)]
        )
    )
    @PostMapping("/template")
    fun saveTemplateDocument(@RequestBody request: FileRequest): ResponseEntity<Unit>
}