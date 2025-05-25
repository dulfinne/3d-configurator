package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.LightSettingsRequest
import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.LightSettingsResponse
import com.dulfinne.configurator.exception.ErrorResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Light settings Controller", description = "Managing light settings")
interface LightSettingsApi {

    @Operation(
        operationId = "getLightSettings",
        summary = "Get light settings"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IconResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @GetMapping
    fun getLightSettings(): LightSettingsResponse

    @Operation(
        operationId = "saveLightSettings",
        summary = "Add light settings"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IconResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "409",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PostMapping
    fun saveLightSettings(@RequestBody request: LightSettingsRequest): ResponseEntity<LightSettingsResponse>


    @Operation(
        operationId = "updateLightSettings",
        summary = "Update light settings"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = IconResponse::class)
                )
            ]
        ),
        ApiResponse(
            responseCode = "404",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )
            ]
        )
    )
    @PutMapping
    fun updateLightSettings(@RequestBody request: LightSettingsRequest): LightSettingsResponse
}