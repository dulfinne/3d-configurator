package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.ElevatorRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Document Controller", description = "Generating documents")
interface DocumentApi {


    @Operation(
        operationId = "generateElevatorDocument",
        summary = "Generate elevator document from configuration"
    )
    @ApiResponses(
        ApiResponse(
            description = "Successfully downloads document",
            responseCode = "200",
            content = [Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)]
        )
    )
    fun generateElevatorConfigurationDocument(@RequestBody request: ElevatorRequest): ResponseEntity<ByteArray>
}