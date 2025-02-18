package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import java.util.UUID

interface TextureService {
    fun getAllTextures(page: Int, size: Int): PaginatedResponse<TextureResponse>
    fun getTextureByUUID(uuid: UUID): TextureResponse
    fun createTexture(request: TextureRequest): TextureResponse
    fun updateTexture(uuid: UUID, request: TextureRequest): TextureResponse
    fun deleteTextureByUUID(uuid: UUID)
}