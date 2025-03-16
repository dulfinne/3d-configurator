package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.ElevatorRequest
import com.dulfinne.configurator.service.DocumetService
import com.dulfinne.configurator.service.ReplacementService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.DocumentConstants
import com.spire.doc.Document
import com.spire.doc.FileFormat
import com.spire.doc.TextWatermark
import io.minio.GetObjectArgs
import io.minio.MinioClient
import org.springframework.stereotype.Service
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.InputStream


@Service
class DocumentServiceImpl(val minioClient: MinioClient, val replacementService: ReplacementService) : DocumetService {
    override fun generateElevatorConfigurationDocument(request: ElevatorRequest): ByteArray {
        return generateFromTemplate(request)
    }

    fun generateFromTemplate(request: ElevatorRequest): ByteArray {
        val inputStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(BucketNames.DOCUMENTS_BUCKET)
                .`object`(DocumentConstants.TEMPLATE_DOCUMENT_NAME)
                .build()
        )

        val document = Document(inputStream)

        replacementService.replaceCabinTextInDocument(document, request.cabin)
        replacementService.replaceWallTextInDocument(document, request.wall)
        replacementService.replaceHandrailTextInDocument(document, request.handrail)
        replacementService.replaceMirrorTextInDocument(document, request.mirror)
        replacementService.replaceDoorsTextInDocument(document, request.doors)
        replacementService.replaceBumpersTextInDocument(document, request.bumpers)
        replacementService.replaceCeilingTextInDocument(document, request.ceiling)
        replacementService.replaceFloorTextInDocument(document, request.floor)
        replacementService.replaceControlPanelTextInDocument(document, request.controlPanel)


        val byteArrayOutputStream = ByteArrayOutputStream()
        document.saveToStream(byteArrayOutputStream, FileFormat.Docx)

        return byteArrayOutputStream.toByteArray()
    }

    private fun addElevatorImage(document: Document) {
        val image: InputStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(BucketNames.DOCUMENTS_BUCKET)
                .`object`(DocumentConstants.TEMPLATE_ELEVATOR_PICTURE_NAME)
                .build()
        )

        val paragraph = document.addSection().addParagraph()
        val text = paragraph.appendText(DocumentConstants.TEMPLATE_PICTURE_DESCRIPTION)
        text.characterFormat.textColor = Color.WHITE

        val picture = paragraph.appendPicture(image)
        picture.width = 300f
        picture.height = 400f
    }

    private fun setWatermark(document: Document) {
        val textWatermark = TextWatermark()
        textWatermark.text = "CONFIDENTIAL"
        textWatermark.fontSize = 72f
        textWatermark.color = Color.RED
        document.watermark = textWatermark
    }
}