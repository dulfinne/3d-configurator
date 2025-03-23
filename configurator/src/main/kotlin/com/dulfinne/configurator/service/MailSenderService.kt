package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.ElevatorRequest

interface MailSenderService {
    fun sendElevatorDocument(to: String, request: ElevatorRequest)
}