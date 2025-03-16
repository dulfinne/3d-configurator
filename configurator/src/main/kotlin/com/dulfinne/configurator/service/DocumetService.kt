package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ElevatorRequest

interface DocumetService {
    fun generateElevatorConfigurationDocument(request: ElevatorRequest): ByteArray
}