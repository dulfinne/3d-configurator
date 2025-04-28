package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumentService
import com.dulfinne.configurator.service.ReplacementService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.DocumentConstants
import com.spire.doc.Document
import com.spire.doc.FileFormat
import com.spire.doc.TextWatermark
import io.minio.GetObjectArgs
import io.minio.MinioClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Color
import java.io.ByteArrayOutputStream

@Service
class DocumentServiceImpl(
    val minioClient: MinioClient,
    val replacementService: ReplacementService,
) : DocumentService {
    override fun generateElevatorDocument(request: ElevatorRequest, file: MultipartFile): ByteArray {
        return generateFromTemplate(request, file)
    }

    fun generateFromTemplate(request: ElevatorRequest, file: MultipartFile): ByteArray {
        val inputStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(BucketNames.DOCUMENTS_BUCKET)
                .`object`(DocumentConstants.TEMPLATE_DOCUMENT_NAME)
                .build()
        )

        val document = Document(inputStream)

        replacementService.replaceDateTextInDocument(document)
        replacementService.replaceCabinTextInDocument(document, request.cabin)
        replacementService.replaceWallTextInDocument(document, request.wall)
        replacementService.replaceHandrailTextInDocument(document, request.handrail)
        replacementService.replaceMirrorTextInDocument(document, request.mirror)
        replacementService.replaceDoorsTextInDocument(document, request.doors)
        replacementService.replaceBumpersTextInDocument(document, request.bumpers)
        replacementService.replaceCeilingTextInDocument(document, request.ceiling)
        replacementService.replaceFloorTextInDocument(document, request.floor)
        replacementService.replaceControlPanelTextInDocument(document, request.controlPanel)
        replacementService.replaceHallTextInDocument(document, request.hall)

        addElevatorImage(document, file)

        val byteArrayOutputStream = ByteArrayOutputStream()
        document.saveToStream(byteArrayOutputStream, FileFormat.PDF)

        return byteArrayOutputStream.toByteArray()
    }

    private fun addElevatorImage(document: Document, image: MultipartFile) {
        val paragraph = document.addSection().addParagraph()

        val picture = paragraph.appendPicture(image.inputStream)
        picture.width = 300f
        picture.height = 300f
    }

    private fun setWatermark(document: Document) {
        val textWatermark = TextWatermark()
        textWatermark.text = "MOVEL"
        textWatermark.fontSize = 72f
        textWatermark.color = Color.BLUE
        document.watermark = textWatermark
    }
}