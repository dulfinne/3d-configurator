package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.FileRequest

interface FileService {
    fun save3DModel(request: FileRequest)
    fun saveDocumentTemplate(request: FileRequest)
}