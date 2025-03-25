package com.dulfinne.configurator.controller.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Elevator Controller", description = "Work with elevators")
interface ElevatorApi {

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
    fun generateElevatorConfigurationDocument(
        @RequestPart("request") requestJson: String,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<ByteArray>

    @Operation(
        operationId = "sendElevatorConfigurationDocumentToMail",
        summary = "Send elevator documents to email"
    )
    @ApiResponses(
        ApiResponse(
            description = "Successfully sent document to mail",
            responseCode = "200"
        )
    )
    fun sendElevatorConfigurationDocumentToMail(
        @RequestParam @NotNull @Email email: String,
        @RequestPart("request") requestJson: String,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Unit>
}