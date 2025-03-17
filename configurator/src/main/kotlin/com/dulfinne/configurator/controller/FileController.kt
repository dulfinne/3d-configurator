package com.dulfinne.configurator.controller

import com.dulfinne.configurator.controller.api.FileApi
import com.dulfinne.configurator.dto.request.FileRequest
import com.dulfinne.configurator.service.FileService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/files")
@CrossOrigin
class FileController(val fileService: FileService) : FileApi {

    @PostMapping("/3d-model")
    override fun save3DModel(@ModelAttribute @Valid request: FileRequest): ResponseEntity<Unit> {
        fileService.save3DModel(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/document-template")
    override fun saveTemplateDocument(@ModelAttribute @Valid request: FileRequest): ResponseEntity<Unit> {
        fileService.saveDocumentTemplate(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}