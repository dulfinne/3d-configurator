package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.DocumentApi
import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumentService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/documents/configurations")
@CrossOrigin
class DocumentController(val documentService: DocumentService) : DocumentApi {

    @PostMapping("/elevator")
    override fun generateElevatorConfigurationDocument(@RequestBody request: ElevatorRequest): ResponseEntity<ByteArray> {
        val wordDocument = documentService.generateElevatorConfigurationDocument(request)
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"elevator-config.pdf\"")
            .body(wordDocument)
    }
}