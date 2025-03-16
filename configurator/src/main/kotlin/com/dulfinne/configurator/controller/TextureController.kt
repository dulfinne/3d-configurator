package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.TextureApi
import com.dulfinne.configurator.dto.request.NameRequest
import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.request.UpdateTextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.service.TextureService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/textures")
@CrossOrigin
class TextureController(val textureService: TextureService) : TextureApi {

    @GetMapping
    override fun getAllTextures(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<TextureResponse> {
        val textureResponsePage = textureService.getAllTextures(page, size)
        return textureResponsePage;
    }

    @GetMapping("/{id}")
    override fun getTexture(@PathVariable id: UUID): TextureResponse {
        val textureResponse = textureService.getTextureById(id)
        return textureResponse;
    }

    @GetMapping("/icon/{id}")
    override fun getTextureByIconId(@PathVariable id: UUID): TextureResponse {
        val textureResponse = textureService.getTextureByIconId(id)
        return textureResponse;
    }

    @PostMapping
    override fun saveTexture(@Valid @ModelAttribute request: TextureRequest): ResponseEntity<TextureResponse> {
        val textureResponse = textureService.createTexture(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(textureResponse)
    }

    @PutMapping("/{uuid}")
    override fun updateTexture(
        @PathVariable uuid: UUID,
        @Valid @ModelAttribute request: UpdateTextureRequest
    ): TextureResponse {
        val textureResponse = textureService.updateTexture(uuid, request)
        return textureResponse;
    }

    @PutMapping("/{uuid}/name")
    override fun updateTextureName(
        @PathVariable uuid: UUID,
        @Valid @ModelAttribute request: NameRequest
    ): TextureResponse {
        val textureResponse = textureService.updateTextureName(uuid, request)
        return textureResponse;
    }

    @DeleteMapping("/{uuid}")
    override fun deleteTexture(@PathVariable uuid: UUID): ResponseEntity<Unit> {
        textureService.deleteTextureById(uuid);
        return ResponseEntity.noContent().build()
    }
}