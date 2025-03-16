package com.dulfinne.configurator.controller.api

import com.dulfinne.configurator.dto.request.NameRequest
import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.request.UpdateTextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
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
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Tag(name = "Texture Controller", description = "Managing textures")
interface TextureApi {

    @Operation(
        summary = "Get all textures with pagination",
        description = "Returns a paginated list of all textures"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all textures",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = PaginatedResponse::class)
            )]
        )
    )
    @GetMapping
    fun getAllTextures(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<TextureResponse>

    @Operation(
        summary = "Get texture by ID",
        description = "Returns a specific texture by its unique ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved texture",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TextureResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Texture not found",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/{id}")
    fun getTexture(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable id: UUID
    ): TextureResponse

    @Operation(
        summary = "Get texture by Icon ID",
        description = "Returns a texture associated with a specific icon ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved texture by icon ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TextureResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Texture not found for the given icon ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @GetMapping("/icon/{id}")
    fun getTextureByIconId(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable id: UUID
    ): TextureResponse

    @Operation(
        summary = "Create a new texture",
        description = "Creates a new texture based on the provided request"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Successfully created texture",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TextureResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Texture already exists cause of same parameters",
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
    fun saveTexture(
        @Valid @ModelAttribute request: TextureRequest
    ): ResponseEntity<TextureResponse>

    @Operation(
        summary = "Update texture by ID",
        description = "Updates the texture information for a given texture ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully updated texture",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TextureResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Texture not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Texture already exists cause of same parameters",
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
    @PutMapping("/{uuid}")
    fun updateTexture(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable uuid: UUID,
        @Valid @ModelAttribute request: UpdateTextureRequest
    ): TextureResponse

    @Operation(
        summary = "Update texture name",
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully updated texture name",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = TextureResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Texture not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        ),
        ApiResponse(
            responseCode = "409",
            description = "Texture name already exists",
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
    @PutMapping("/{uuid}/name")
    fun updateTextureName(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable uuid: UUID,
        @Valid @ModelAttribute request: NameRequest
    ): TextureResponse

    @Operation(
        summary = "Delete texture by ID",
        description = "Deletes the texture associated with the given ID"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "Successfully deleted texture"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Texture not found for the given ID",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = ErrorResponse::class)
            )]
        )
    )
    @DeleteMapping("/{uuid}")
    fun deleteTexture(
        @Parameter(`in` = ParameterIn.PATH)
        @PathVariable uuid: UUID
    ): ResponseEntity<Unit>
}

