package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.configuration.BumpersRequest
import com.dulfinne.configurator.dto.request.configuration.CabinRequest
import com.dulfinne.configurator.dto.request.configuration.CeilingRequest
import com.dulfinne.configurator.dto.request.configuration.ControlPanelRequest
import com.dulfinne.configurator.dto.request.configuration.DoorsRequest
import com.dulfinne.configurator.dto.request.configuration.FloorRequest
import com.dulfinne.configurator.dto.request.configuration.HandrailRequest
import com.dulfinne.configurator.dto.request.configuration.MirrorRequest
import com.dulfinne.configurator.dto.request.configuration.WallRequest
import com.dulfinne.configurator.entity.enums.ReplacementOption
import com.dulfinne.configurator.entity.enums.DocumentKey
import com.dulfinne.configurator.service.ReplacementService
import com.dulfinne.configurator.service.TextureService
import com.dulfinne.configurator.util.LocationConstants
import com.spire.doc.Document
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ReplacementServiceImpl(val textureService: TextureService) : ReplacementService {
    override fun replaceDateTextInDocument(document: Document) {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = currentDate.format(formatter)

        document.replace("{${DocumentKey.ANNA_DATE}}", formattedDate, false, false)
    }

    override fun replaceCabinTextInDocument(document: Document, cabin: CabinRequest) {
        document.replace("{${DocumentKey.CABIN_SIZE}}", ReplacementOption.toDisplayName(cabin.size), false, false)
        document.replace("{${DocumentKey.CABIN_TYPE}}", ReplacementOption.toDisplayName(cabin.type), false, false)
        document.replace(
            "{${DocumentKey.CABIN_OPENING_TYPE}}",
            ReplacementOption.toDisplayName(cabin.openingType),
            false,
            false
        )
    }

    override fun replaceWallTextInDocument(document: Document, wall: WallRequest) {
        document.replace("{${DocumentKey.WALL_BACK_MATERIAL}}", getTextureNameByID(wall.back), false, false)
        document.replace("{${DocumentKey.WALL_FRONT_MATERIAL}}", getTextureNameByID(wall.front), false, false)
        document.replace("{${DocumentKey.WALL_LEFT_MATERIAL}}", getTextureNameByID(wall.left), false, false)
        document.replace("{${DocumentKey.WALL_RIGHT_MATERIAL}}", getTextureNameByID(wall.right), false, false)
    }

    override fun replaceHandrailTextInDocument(document: Document, handrail: HandrailRequest) {
        document.replace(
            "{${DocumentKey.HANDRAIL_EXISTENCE}}", ReplacementOption.toDisplayName(handrail.existence), false, false
        )
        document.replace("{${DocumentKey.HANDRAIL_MATERIAL}}", getTextureNameByID(handrail.texture), false, false)
        document.replace(
            "{${DocumentKey.HANDRAIL_TYPE}}", ReplacementOption.toDisplayName(handrail.type), false, false
        )
        document.replace(
            "{${DocumentKey.HANDRAIL_LOCATION}}",
            getLocationText(handrail.left, handrail.right, handrail.back),
            false,
            false
        )
    }

    override fun replaceMirrorTextInDocument(document: Document, mirror: MirrorRequest) {
        document.replace(
            "{${DocumentKey.MIRROR_EXISTENCE}}", ReplacementOption.toDisplayName(mirror.existence), false, false
        )
        document.replace("{${DocumentKey.MIRROR_TYPE}}", ReplacementOption.toDisplayName(mirror.type), false, false)
        document.replace(
            "{${DocumentKey.MIRROR_LOCATION}}",
            getLocationText(mirror.left, mirror.right, mirror.back),
            false,
            false
        )
    }

    override fun replaceDoorsTextInDocument(document: Document, doors: DoorsRequest) {
        document.replace("{${DocumentKey.DOORS_MATERIAL}}", getTextureNameByID(doors.texture), false, false)
    }

    override fun replaceBumpersTextInDocument(document: Document, bumpers: BumpersRequest) {
        document.replace("{${DocumentKey.BUMPERS_MATERIAL}}", getTextureNameByID(bumpers.texture), false, false)
    }

    override fun replaceCeilingTextInDocument(document: Document, ceiling: CeilingRequest) {
        document.replace("{${DocumentKey.CEILING_LAMP}}", getTextureNameByID(ceiling.lamp), false, false)
        document.replace("{${DocumentKey.CEILING_MATERIAL}}", getTextureNameByID(ceiling.texture), false, false)
    }

    override fun replaceFloorTextInDocument(document: Document, floor: FloorRequest) {
        document.replace("{${DocumentKey.FLOOR_MATERIAL}}", getTextureNameByID(floor.texture), false, false)
    }

    override fun replaceControlPanelTextInDocument(document: Document, controlPanel: ControlPanelRequest) {
        document.replace(
            "{${DocumentKey.CONTROL_PANEL_TYPE}}", ReplacementOption.toDisplayName(controlPanel.type), false, false
        )
        document.replace(
            "{${DocumentKey.CONTROL_PANEL_NAME}}", getTextureNameByID(controlPanel.indicationBoard), false, false
        )
        document.replace(
            "{${DocumentKey.CONTROL_PANEL_SIDE}}", ReplacementOption.toDisplayName(controlPanel.side), false, false
        )
        document.replace(
            "{${DocumentKey.CONTROL_PANEL_LOCATION}}",
            ReplacementOption.toDisplayName(controlPanel.location),
            false,
            false
        )
        document.replace(
            "{${DocumentKey.CONTROL_PANEL_MATERIAL}}", getTextureNameByID(controlPanel.texture), false, false
        )
    }

    private fun getTextureNameByID(id: UUID): String {
        val texture = textureService.getTextureById(id)
        return texture.name
    }

    private fun getLocationText(left: Boolean, right: Boolean, back: Boolean): String {
        val locationList = mutableListOf<String>()

        if (left) locationList.add(LocationConstants.ON_LEFT_SIDE)
        if (right) locationList.add(LocationConstants.ON_RIGHT_SIDE)
        if (back) locationList.add(LocationConstants.ON_BACK_SIDE)

        return locationList.joinToString("\n")
    }
}