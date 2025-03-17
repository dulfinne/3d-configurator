package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ElevatorRequest

interface DocumentService {
    fun generateElevatorConfigurationDocument(request: ElevatorRequest): ByteArray
}