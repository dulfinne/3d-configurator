package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Icon Controller", description = "Managing icons")
interface IconApi {

    @Operation(
        operationId = "getAllIcons",
        summary = "Get all icons with pagination"
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
        )
    )
    @GetMapping
    fun getAllIcons(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<IconResponse>
}