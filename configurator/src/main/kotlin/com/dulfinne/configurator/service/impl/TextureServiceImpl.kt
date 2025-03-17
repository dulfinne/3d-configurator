package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.NameRequest
import com.dulfinne.configurator.dto.request.TextureRequest
import com.dulfinne.configurator.dto.request.UpdateTextureRequest
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

        saveImages(texture, request, request.name)

        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun updateTexture(id: UUID, request: UpdateTextureRequest): TextureResponse {
        var texture = getTextureIfExists(id)
        val currentIconName = texture.icon.name
        val newIconName = request.icon.name

        checkIconNameUniqueness(newIconName, currentIconName)
        texture.updateFromRequest(request)
        uploadImagesIfNeeded(texture, request)

        texture = textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun updateTextureName(id: UUID, request: NameRequest): TextureResponse {
        val texture = getTextureIfExists(id)

        val oldName = texture.name
        val newName = request.name
        checkNameUniqueness(newName, oldName)
        updateTextureFields(texture, newName, oldName)

        textureRepository.save(texture)
        return texture.toResponse()
    }

    @Transactional
    override fun deleteTextureById(id: UUID) {
        val texture = getTextureIfExists(id)
        textureRepository.delete(texture)
        deleteAllImagesFromStorage(texture.name)
    }

    private fun deleteAllImagesFromStorage(textureName: String) {
        imageService.deleteImage(BucketNames.TEXTURE_BUCKET, textureName)
        imageService.deleteImage(BucketNames.BUMP_MAP_BUCKET, textureName)
        imageService.deleteImage(BucketNames.ALPHA_MAP_BUCKET, textureName)
        imageService.deleteImage(BucketNames.NORMAL_BUCKET, textureName)
        imageService.deleteImage(BucketNames.METALNESS_BUCKET, textureName)
        imageService.deleteImage(BucketNames.ROUGHNESS_BUCKET, textureName)
        imageService.deleteImage(BucketNames.AO_BUCKET, textureName)
        imageService.deleteImage(BucketNames.DISPLACEMENT_BUCKET, textureName)
        imageService.deleteImage(BucketNames.ICON_BUCKET, textureName)
    }

    private fun saveImages(texture: Texture, request: TextureRequest, textureName: String) {
        val baseTextureUrl =
            request.baseTexture?.let { imageService.uploadImage(BucketNames.TEXTURE_BUCKET, textureName, it) }
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
        val iconUrl = request.icon.icon?.let { imageService.uploadImage(BucketNames.ICON_BUCKET, textureName, it) }

        texture.apply {
            this.baseTextureUrl = baseTextureUrl
            this.bumpMapUrl = bumpMapUrl
            this.alphaMapUrl = alphaMapUrl
            this.normalMapUrl = normalMapUrl
            this.roughnessMapUrl = roughnessMapUrl
            this.metalnessMapUrl = metalnessMapUrl
            this.aoMapUrl = aoMapUrl
            this.displacementMapUrl = displacementMapUrl
            this.icon.url = iconUrl ?: ""
        }
    }

    private fun uploadImagesIfNeeded(texture: Texture, request: UpdateTextureRequest) {
        val textureName = texture.name
        val baseTextureUrl = imageService.uploadOrKeep(BucketNames.TEXTURE_BUCKET, textureName, request.baseTexture, texture.baseTextureUrl, request.removeBaseTexture)
        val bumpMapUrl = imageService.uploadOrKeep(BucketNames.BUMP_MAP_BUCKET, textureName, request.bumpMap, texture.bumpMapUrl, request.removeBumpMap)
        val alphaMapUrl = imageService.uploadOrKeep(BucketNames.ALPHA_MAP_BUCKET, textureName, request.alphaMap, texture.alphaMapUrl, request.removeAlphaMap)
        val normalMapUrl = imageService.uploadOrKeep(BucketNames.NORMAL_BUCKET, textureName, request.normalMap, texture.normalMapUrl, request.removeNormalMap)
        val roughnessMapUrl = imageService.uploadOrKeep(BucketNames.ROUGHNESS_BUCKET, textureName, request.roughnessMap, texture.roughnessMapUrl, request.removeRoughnessMap)
        val metalnessMapUrl = imageService.uploadOrKeep(BucketNames.METALNESS_BUCKET, textureName, request.metalnessMap, texture.metalnessMapUrl, request.removeMetalnessMap)
        val aoMapUrl = imageService.uploadOrKeep(BucketNames.AO_BUCKET, textureName, request.aoMap, texture.aoMapUrl, request.removeAoMap)
        val displacementMapUrl = imageService.uploadOrKeep(BucketNames.DISPLACEMENT_BUCKET, textureName, request.displacementMap, texture.displacementMapUrl, request.removeDisplacementMap)
        val iconUrl = imageService.uploadOrKeep(BucketNames.ICON_BUCKET, textureName, request.icon.icon, texture.icon.url, false)

        texture.apply {
            this.baseTextureUrl = baseTextureUrl
            this.bumpMapUrl = bumpMapUrl
            this.alphaMapUrl = alphaMapUrl
            this.normalMapUrl = normalMapUrl
            this.roughnessMapUrl = roughnessMapUrl
            this.metalnessMapUrl = metalnessMapUrl
            this.aoMapUrl = aoMapUrl
            this.displacementMapUrl = displacementMapUrl
            this.icon.url = iconUrl ?: ""
        }
    }
    private fun updateTextureFields(texture: Texture, newName: String, oldName: String) {
        val newBaseTextureUrl = imageService.renameFile(BucketNames.TEXTURE_BUCKET, newName, oldName,texture.baseTextureUrl)
        val newBumpMapUrl = imageService.renameFile(BucketNames.BUMP_MAP_BUCKET, newName, oldName, texture.bumpMapUrl)
        val newAlphaMapUrl = imageService.renameFile(BucketNames.ALPHA_MAP_BUCKET, newName, oldName, texture.alphaMapUrl)
        val newNormalMapUrl = imageService.renameFile(BucketNames.NORMAL_BUCKET, newName, oldName, texture.normalMapUrl)
        val newMetalnessMapUrl = imageService.renameFile(BucketNames.METALNESS_BUCKET, newName, oldName, texture.metalnessMapUrl)
        val newRoughnessMapUrl = imageService.renameFile(BucketNames.ROUGHNESS_BUCKET, newName, oldName, texture.roughnessMapUrl)
        val newAoMapUrl = imageService.renameFile(BucketNames.AO_BUCKET, newName, oldName, texture.aoMapUrl)
        val newDisplacementMapUrl = imageService.renameFile(BucketNames.DISPLACEMENT_BUCKET, newName, oldName, texture.displacementMapUrl)
        val newIconUrl = imageService.renameFile(BucketNames.ICON_BUCKET, newName, oldName, texture.icon.url)

        texture.apply {
            this.name = newName
            this.baseTextureUrl = newBaseTextureUrl
            this.bumpMapUrl = newBumpMapUrl
            this.alphaMapUrl = newAlphaMapUrl
            this.normalMapUrl = newNormalMapUrl
            this.metalnessMapUrl = newMetalnessMapUrl
            this.roughnessMapUrl = newRoughnessMapUrl
            this.aoMapUrl = newAoMapUrl
            this.displacementMapUrl = newDisplacementMapUrl
            this.icon.url = newIconUrl ?: ""
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