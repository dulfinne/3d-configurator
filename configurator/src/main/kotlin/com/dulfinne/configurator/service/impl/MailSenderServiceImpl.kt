package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumentService
import com.dulfinne.configurator.service.MailSenderService
import com.dulfinne.configurator.util.DocumentConstants
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MailSenderServiceImpl(val mailSender: JavaMailSender, val documentService: DocumentService) : MailSenderService {
    @Value("\${spring.mail.username}")
    lateinit var myFrom: String

    override fun sendElevatorDocument(to: String, request: ElevatorRequest, file: MultipartFile, requestJson: String) {
        val document = ByteArrayResource(documentService.generateElevatorDocument(request, file))
        val configJson = jacksonObjectMapper().writeValueAsBytes(request)
        val originalJson = ByteArrayResource(requestJson.toByteArray())

        val mimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage, true)
        helper.apply {
            setTo(to)
            setFrom(myFrom)
            setSubject(DocumentConstants.DEFAULT_SUBJECT)
            setText(DocumentConstants.DEFAULT_TEXT, false)
            addAttachment(DocumentConstants.DEFAULT_QUESTION_FILE_NAME, document)
            addAttachment(DocumentConstants.DEFAULT_CONFIG_FILE_NAME, originalJson)
        }
        mailSender.send(mimeMessage)
    }
}

