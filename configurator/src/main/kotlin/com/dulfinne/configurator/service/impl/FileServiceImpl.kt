package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.FileRequest
import com.dulfinne.configurator.service.FileService
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.util.BucketNames
import org.springframework.stereotype.Service

@Service
class FileServiceImpl(val imageService: ImageService) : FileService {

    override fun save3DModel(request: FileRequest) {
        request.file?.let { imageService.uploadImage(BucketNames.MODELS_3D_BUCKET, it) }
    }

    override fun saveDocumentTemplate(request: FileRequest) {
        request.file?.let { imageService.uploadImage(BucketNames.DOCUMENTS_BUCKET, it) }
    }
}