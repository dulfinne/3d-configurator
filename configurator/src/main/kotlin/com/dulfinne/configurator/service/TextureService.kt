package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import java.util.UUID

interface TextureService {
    fun getAllTextures(page: Int, size: Int): PaginatedResponse<TextureResponse>
    fun getTextureById(id: UUID): TextureResponse
    fun getTextureByIconId(id: UUID): TextureResponse
    fun createTexture(request: TextureRequest): TextureResponse
    fun updateTexture(id: UUID, request: TextureRequest): TextureResponse
    fun deleteTextureById(id: UUID)
}