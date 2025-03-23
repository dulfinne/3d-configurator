package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.ElevatorApi
import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumentService
import com.dulfinne.configurator.service.MailSenderService
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/elevators/documents")
@CrossOrigin
@Validated
class ElevatorController(val documentService: DocumentService, val mailSender: MailSenderService) : ElevatorApi {

    @PostMapping
    override fun generateElevatorConfigurationDocument(@RequestBody request: ElevatorRequest): ResponseEntity<ByteArray> {
        val wordDocument = documentService.generateElevatorDocument(request)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"elevator-config.pdf\"")
            .body(wordDocument)
    }

    @PostMapping("/email")
    override fun sendElevatorConfigurationDocumentToMail(
        @RequestParam @NotNull @Email email: String?,
        @RequestBody request: ElevatorRequest
    ): ResponseEntity<Unit> {
        mailSender.sendElevatorDocument(email!!, request)
        return ResponseEntity.ok().build()
    }
}