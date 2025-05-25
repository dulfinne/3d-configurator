package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.configuration.BumpersRequest
import com.dulfinne.configurator.dto.request.configuration.CabinRequest
import com.dulfinne.configurator.dto.request.configuration.CeilingRequest
import com.dulfinne.configurator.dto.request.configuration.ControlPanelRequest
import com.dulfinne.configurator.dto.request.configuration.DoorsRequest
import com.dulfinne.configurator.dto.request.configuration.FloorRequest
import com.dulfinne.configurator.dto.request.configuration.HallRequest
import com.dulfinne.configurator.dto.request.configuration.HandrailRequest
import com.dulfinne.configurator.dto.request.configuration.MirrorRequest
import com.dulfinne.configurator.dto.request.configuration.WallRequest
import com.dulfinne.configurator.entity.enums.ReplacementOption
import com.dulfinne.configurator.entity.enums.DocumentKey
import com.dulfinne.configurator.service.ProjectTemplateService
import com.dulfinne.configurator.service.ReplacementService
import com.dulfinne.configurator.service.TextureService
import com.dulfinne.configurator.util.DocumentConstants
import com.dulfinne.configurator.util.LocationConstants
import com.spire.doc.Document
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ReplacementServiceImpl(val textureService: TextureService, val projectTemplateService: ProjectTemplateService) : ReplacementService {
    override fun replaceDateTextInDocument(document: Document) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = currentDate.format(formatter)

        document.replace("{${DocumentKey.ANNA_DATE}}", formattedDate, false, false)
    }

    override fun replaceCabinTextInDocument(document: Document, cabin: CabinRequest) {
        mapOf(
            DocumentKey.CABIN_SIZE to cabin.size,
            DocumentKey.CABIN_TYPE to cabin.type,
            DocumentKey.CABIN_OPENING_TYPE to cabin.openingType,
        ).forEach { (key, value) ->
            document.replace("{$key}", ReplacementOption.toDisplayName(value), false, false)
        }
        document.replace("{${DocumentKey.DESIGN_PROJECT}}", getProjectTemplateNameById(cabin.designProject), false, false)
    }

    override fun replaceWallTextInDocument(document: Document, wall: WallRequest) {
        mapOf(
            DocumentKey.WALL_BACK_MATERIAL to wall.back,
            DocumentKey.WALL_FRONT_MATERIAL to wall.front,
            DocumentKey.WALL_LEFT_MATERIAL to wall.left,
            DocumentKey.WALL_RIGHT_MATERIAL to wall.right
        ).forEach { (key, textureId) ->
            document.replace("{$key}", getTextureNameByID(textureId), false, false)
        }
    }

    override fun replaceHandrailTextInDocument(document: Document, handrail: HandrailRequest) {
        val exists = handrail.existence == ReplacementOption.HAVE_HAND.key

        val material = if (exists) getTextureNameByID(handrail.texture) else ""
        val type = if (exists) ReplacementOption.toDisplayName(handrail.type) else ""
        val location = if (exists) getLocationText(handrail.left, handrail.right, handrail.back) else ""

        mapOf(
            DocumentKey.HANDRAIL_EXISTENCE to ReplacementOption.toDisplayName(handrail.existence),
            DocumentKey.HANDRAIL_MATERIAL to material,
            DocumentKey.HANDRAIL_TYPE to type,
            DocumentKey.HANDRAIL_LOCATION to location
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    override fun replaceMirrorTextInDocument(document: Document, mirror: MirrorRequest) {
        val exists = mirror.existence == ReplacementOption.HAVE_MIRROR.key

        val type = if (exists) ReplacementOption.toDisplayName(mirror.type) else ""
        val location = if (exists) getLocationText(mirror.left, mirror.right, mirror.back) else ""

        mapOf(
            DocumentKey.MIRROR_EXISTENCE to ReplacementOption.toDisplayName(mirror.existence),
            DocumentKey.MIRROR_TYPE to type,
            DocumentKey.MIRROR_LOCATION to location
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    override fun replaceDoorsTextInDocument(document: Document, doors: DoorsRequest) {
        document.replace("{${DocumentKey.DOORS_MATERIAL}}", getTextureNameByID(doors.texture), false, false)
    }

    override fun replaceBumpersTextInDocument(document: Document, bumpers: BumpersRequest) {
        document.replace("{${DocumentKey.BUMPERS_MATERIAL}}", getTextureNameByID(bumpers.texture), false, false)
    }

    override fun replaceCeilingTextInDocument(document: Document, ceiling: CeilingRequest) {
        mapOf(
            DocumentKey.CEILING_LAMP to getTextureNameByID(ceiling.lamp),
            DocumentKey.CEILING_MATERIAL to getTextureNameByID(ceiling.texture)
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    override fun replaceFloorTextInDocument(document: Document, floor: FloorRequest) {
        document.replace("{${DocumentKey.FLOOR_MATERIAL}}", getTextureNameByID(floor.texture), false, false)
    }

    override fun replaceControlPanelTextInDocument(document: Document, controlPanel: ControlPanelRequest) {
        mapOf(
            DocumentKey.CONTROL_PANEL_TYPE to ReplacementOption.toDisplayName(controlPanel.type),
            DocumentKey.CONTROL_PANEL_NAME to getTextureNameByID(controlPanel.indicationBoard),
            DocumentKey.CONTROL_PANEL_SIDE to ReplacementOption.toDisplayName(controlPanel.side),
            DocumentKey.CONTROL_PANEL_LOCATION to ReplacementOption.toDisplayName(controlPanel.location),
            DocumentKey.CONTROL_PANEL_MATERIAL to getTextureNameByID(controlPanel.texture)
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    override fun replaceHallTextInDocument(document: Document, hall: HallRequest?) {
        if (hall == null) {
            replaceNotConfiguredHallTextInDocument(document)
            return
        }

        if (hall.frameExistence == ReplacementOption.NO_PORTAL.key) {
            document.replace("{${DocumentKey.CALL_POST_FRAME_MATERIAL}}", "", false, false)
        }

        mapOf(
            DocumentKey.CALL_POST_TYPE to ReplacementOption.toDisplayName(hall.callPostType),
            DocumentKey.CALL_POST_FRAME to ReplacementOption.toDisplayName(hall.frameExistence),
            DocumentKey.CALL_POST_FRAME_MATERIAL to getTextureNameByID(hall.frameTexture),
            DocumentKey.IND_BOARD_TYPE to ReplacementOption.toDisplayName(hall.indicationBoardType),
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    private fun replaceNotConfiguredHallTextInDocument(document: Document) {
        mapOf(
            DocumentKey.CALL_POST_TYPE to DocumentConstants.NOT_CONFIGURED,
            DocumentKey.CALL_POST_FRAME to "",
            DocumentKey.CALL_POST_FRAME_MATERIAL to "",
            DocumentKey.IND_BOARD_TYPE to DocumentConstants.NOT_CONFIGURED,
        ).forEach { (key, value) ->
            document.replace("{$key}", value, false, false)
        }
    }

    private fun getTextureNameByID(id: UUID): String {
        val texture = textureService.getTextureById(id)
        return texture.name
    }

    private fun getProjectTemplateNameById(id: UUID): String {
        val project = projectTemplateService.getProjectTemplateById(id)
        return project.name
    }

    private fun getLocationText(left: Boolean, right: Boolean, back: Boolean): String {
        val locationList = mutableListOf<String>()

        if (left) locationList.add(LocationConstants.ON_LEFT_SIDE)
        if (right) locationList.add(LocationConstants.ON_RIGHT_SIDE)
        if (back) locationList.add(LocationConstants.ON_BACK_SIDE)

        return locationList.joinToString("\n")
    }
}