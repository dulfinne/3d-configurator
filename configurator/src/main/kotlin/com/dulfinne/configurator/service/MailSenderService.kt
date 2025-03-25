package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ElevatorRequest
import org.springframework.web.multipart.MultipartFile

interface MailSenderService {
    fun sendElevatorDocument(to: String, request: ElevatorRequest, file: MultipartFile)
}