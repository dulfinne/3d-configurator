package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.ElevatorApi
import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumentService
import com.dulfinne.configurator.service.MailSenderService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/elevators/documents")
@CrossOrigin
@Validated
class ElevatorController(
    val documentService: DocumentService,
    val mailSender: MailSenderService,
    val objectMapper: ObjectMapper
) : ElevatorApi {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun generateElevatorConfigurationDocument(
        @RequestPart("request") requestJson: String,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<ByteArray> {
        val request = objectMapper.readValue(requestJson, ElevatorRequest::class.java)
        val wordDocument = documentService.generateElevatorDocument(request, file)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"elevator-config.pdf\"")
            .body(wordDocument)
    }

    @PostMapping("/email", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun sendElevatorConfigurationDocumentToMail(
        @RequestParam @NotNull @Email email: String,
        @RequestPart("request") requestJson: String,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Unit> {
        val request = objectMapper.readValue(requestJson, ElevatorRequest::class.java)
        mailSender.sendElevatorDocument(email, request, file)
        return ResponseEntity.ok().build()
    }
}