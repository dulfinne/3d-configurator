package com.dulfinne.configurator.controller

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.service.TextureService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
@RequestMapping("/api/v1/textures")
class TextureController(val textureService: TextureService) {

    @GetMapping()
    fun getAllTextures(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "6") size: Int
    ): PaginatedResponse<TextureResponse> {
        val textureResponsePage = textureService.getAllTextures(page, size)
        return textureResponsePage;
    }

    @GetMapping("/{uuid}")
    fun getTexture(@PathVariable uuid: UUID): TextureResponse {
        val textureResponse = textureService.getTextureByUUID(uuid)
        return textureResponse;
    }

    @PostMapping
    fun saveTexture(@RequestBody request: TextureRequest): ResponseEntity<TextureResponse> {
        val textureResponse = textureService.createTexture(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(textureResponse)
    }

    @PutMapping("/{uuid}")
    fun updateTexture(
        @PathVariable uuid: UUID,
        @RequestBody request: TextureRequest
    ): TextureResponse {
        val textureResponse = textureService.updateTexture(uuid, request)
        return textureResponse;
    }

    @DeleteMapping("/{uuid}")
    fun deleteTexture(@PathVariable uuid: UUID): ResponseEntity<Unit> {
        textureService.deleteTextureByUUID(uuid);
        return ResponseEntity.noContent().build()
    }
}