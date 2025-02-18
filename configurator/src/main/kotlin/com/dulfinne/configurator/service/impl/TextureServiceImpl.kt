package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.entity.Texture
import com.dulfinne.configurator.mapper.toEntity
import com.dulfinne.configurator.mapper.toPaginatedResponse
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.mapper.updateFromRequest
import com.dulfinne.configurator.repository.TextureRepository
import com.dulfinne.configurator.service.TextureService
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TextureServiceImpl(val textureRepository: TextureRepository) : TextureService {

    @Transactional(readOnly = true)
    override fun getAllTextures(page: Int, size: Int): PaginatedResponse<TextureResponse> {
        val ridesPage = textureRepository.findAllWithProperties(PageRequest.of(page, size))
        return ridesPage.map { it.toResponse() }.toPaginatedResponse()
    }

    @Transactional(readOnly = true)
    override fun getTextureByUUID(uuid: UUID): TextureResponse {
        val texture = getTextureIfExists(uuid)
        return texture.toResponse()
    }

    @Transactional
    override fun createTexture(request: TextureRequest): TextureResponse {
        var texture = request.toEntity()
        checkNameUniqueness(texture.name)
        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun updateTexture(uuid: UUID, request: TextureRequest): TextureResponse {
        var texture = getTextureIfExists(uuid)
        checkNameUniqueness(request.name, texture.name)
        texture.updateFromRequest(request)

        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun deleteTextureByUUID(uuid: UUID) {
        val texture = getTextureIfExists(uuid)
        textureRepository.delete(texture)
    }

    private fun getTextureIfExists(uuid: UUID): Texture {
        return textureRepository.findByIdOrNull(uuid) ?: throw EntityNotFoundException("Texture not found: $uuid")
    }

    private fun checkNameUniqueness(name: String) {
        textureRepository.findByName(name)?.let {
            throw EntityExistsException("Texture with name '$name' already exists")
        }
    }

    private fun checkNameUniqueness(newName: String, currentName: String) {
        if (!newName.equals(currentName, ignoreCase = true)) {
            checkNameUniqueness(newName)
        }
    }
}