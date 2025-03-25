package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ElevatorRequest
import org.springframework.web.multipart.MultipartFile

interface DocumentService {
    fun generateElevatorDocument(request: ElevatorRequest, file: MultipartFile): ByteArray
}