package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.dto.response.TextureResponse
import com.dulfinne.configurator.entity.Texture
import com.dulfinne.configurator.mapper.toEntity
import com.dulfinne.configurator.mapper.toPaginatedResponse
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.mapper.updateFromRequest
import com.dulfinne.configurator.repository.IconRepository
import com.dulfinne.configurator.repository.TextureRepository
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.service.TextureService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.ExceptionMessages
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TextureServiceImpl(
    val textureRepository: TextureRepository,
    val iconRepository: IconRepository,
    val imageService: ImageService
) : TextureService {

    @Transactional(readOnly = true)
    override fun getAllTextures(page: Int, size: Int): PaginatedResponse<TextureResponse> {
        val ridesPage = textureRepository.findAllWithProperties(PageRequest.of(page, size))
        return ridesPage.map { it.toResponse() }.toPaginatedResponse()
    }

    @Transactional(readOnly = true)
    override fun getTextureById(id: UUID): TextureResponse {
        val texture = getTextureIfExists(id)
        return texture.toResponse()
    }

    @Transactional(readOnly = true)
    override fun getTextureByIconId(id: UUID): TextureResponse {
        val texture = getTextureIfExistsByIconId(id)
        return texture.toResponse()
    }

    @Transactional
    override fun createTexture(request: TextureRequest): TextureResponse {
        var texture = request.toEntity()
        checkNameUniqueness(texture.name)
        checkIconNameUniqueness(texture.icon.name)

        uploadImages(texture, request, request.name, request.icon.name)

        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun updateTexture(id: UUID, request: TextureRequest): TextureResponse {
        var texture = getTextureIfExists(id)

        val currentTextureName = texture.name
        val newTextureName = request.name
        val currentIconName = texture.icon.name
        val newIconName = request.icon.name

        checkNameUniqueness(newTextureName, currentTextureName)
        checkIconNameUniqueness(newIconName, currentIconName)
        texture.updateFromRequest(request)

        deleteOldImagesIfNeeded(currentTextureName, currentIconName, newTextureName, newIconName)
        uploadImages(texture, request, newTextureName, newIconName)

        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun deleteTextureById(id: UUID) {
        val texture = getTextureIfExists(id)
        textureRepository.delete(texture)
        deleteAllImagesFromStorage(texture.name, texture.icon.name)

    }

    private fun deleteAllImagesFromStorage(textureName: String, iconName: String) {
        deleteMapImagesFromStorage(textureName)
        imageService.deleteImage(BucketNames.ICON_BUCKET, iconName)
    }

    private fun deleteMapImagesFromStorage(textureName: String) {
        imageService.deleteImage(BucketNames.TEXTURE_BUCKET, textureName)
        imageService.deleteImage(BucketNames.BUMP_MAP_BUCKET, textureName)
        imageService.deleteImage(BucketNames.ALPHA_MAP_BUCKET, textureName)
        imageService.deleteImage(BucketNames.NORMAL_BUCKET, textureName)
        imageService.deleteImage(BucketNames.METALNESS_BUCKET, textureName)
        imageService.deleteImage(BucketNames.ROUGHNESS_BUCKET, textureName)
        imageService.deleteImage(BucketNames.AO_BUCKET, textureName)
        imageService.deleteImage(BucketNames.DISPLACEMENT_BUCKET, textureName)
    }

    private fun deleteOldImagesIfNeeded(
        currentTextureName: String,
        currentIconName: String,
        newTextureName: String,
        newIconName: String
    ) {
        val nameChanged = currentTextureName != newTextureName
        val iconNameChanged = currentIconName != newIconName

        if (nameChanged) {
            deleteMapImagesFromStorage(currentTextureName)
        }

        if (iconNameChanged) {
            imageService.deleteImage(BucketNames.ICON_BUCKET, currentIconName)
        }
    }

    private fun uploadImages(texture: Texture, request: TextureRequest, textureName: String, iconName: String) {
        val baseTextureUrl = imageService.uploadImage(BucketNames.TEXTURE_BUCKET, textureName, request.baseTexture)
        val bumpMapUrl = request.bumpMap?.let { imageService.uploadImage(BucketNames.BUMP_MAP_BUCKET, textureName, it) }
        val alphaMapUrl =
            request.alphaMap?.let { imageService.uploadImage(BucketNames.ALPHA_MAP_BUCKET, textureName, it) }
        val normalMapUrl =
            request.normalMap?.let { imageService.uploadImage(BucketNames.NORMAL_BUCKET, textureName, it) }
        val roughnessMapUrl =
            request.roughnessMap?.let { imageService.uploadImage(BucketNames.ROUGHNESS_BUCKET, textureName, it) }
        val metalnessMapUrl =
            request.metalnessMap?.let { imageService.uploadImage(BucketNames.METALNESS_BUCKET, textureName, it) }
        val aoMapUrl = request.aoMap?.let { imageService.uploadImage(BucketNames.AO_BUCKET, textureName, it) }
        val displacementMapUrl =
            request.displacementMap?.let { imageService.uploadImage(BucketNames.DISPLACEMENT_BUCKET, textureName, it) }
        val iconUrl = imageService.uploadImage(BucketNames.ICON_BUCKET, iconName, request.icon.icon)

        texture.apply {
            this.baseTextureUrl = baseTextureUrl
            this.bumpMapUrl = bumpMapUrl
            this.alphaMapUrl = alphaMapUrl
            this.normalMapUrl = normalMapUrl
            this.roughnessMapUrl = roughnessMapUrl
            this.metalnessMapUrl = metalnessMapUrl
            this.aoMapUrl = aoMapUrl
            this.displacementMapUrl = displacementMapUrl
            this.icon.url = iconUrl
        }
    }

    private fun getTextureIfExists(id: UUID): Texture {
        return textureRepository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ExceptionMessages.TEXTURE_NOT_FOUND_ID.format(id))
    }

    private fun getTextureIfExistsByIconId(id: UUID): Texture {
        return textureRepository.findByIconId(id)
            ?: throw EntityNotFoundException(ExceptionMessages.TEXTURE_NOT_FOUND_ICON_ID.format(id))
    }

    private fun checkNameUniqueness(name: String) {
        textureRepository.findByName(name)?.let {
            throw EntityExistsException(ExceptionMessages.TEXTURE_EXISTS_NAME.format(name))
        }
    }

    private fun checkNameUniqueness(newName: String, currentName: String) {
        if (!newName.equals(currentName, ignoreCase = true)) {
            checkNameUniqueness(newName)
        }
    }

    private fun checkIconNameUniqueness(name: String) {
        iconRepository.findByName(name)?.let {
            throw EntityExistsException(ExceptionMessages.ICON_EXISTS_NAME.format(name))
        }
    }

    private fun checkIconNameUniqueness(newName: String, currentName: String) {
        if (!newName.equals(currentName, ignoreCase = true)) {
            checkIconNameUniqueness(newName)
        }
    }
}