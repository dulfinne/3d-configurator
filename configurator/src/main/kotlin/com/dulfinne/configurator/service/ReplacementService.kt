package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.request.configuration.BumpersRequest
import com.dulfinne.configurator.dto.request.configuration.CabinRequest
import com.dulfinne.configurator.dto.request.configuration.CeilingRequest
import com.dulfinne.configurator.dto.request.configuration.ControlPanelRequest
import com.dulfinne.configurator.dto.request.configuration.DoorsRequest
import com.dulfinne.configurator.dto.request.configuration.FloorRequest
import com.dulfinne.configurator.dto.request.configuration.HandrailRequest
import com.dulfinne.configurator.dto.request.configuration.MirrorRequest
import com.dulfinne.configurator.dto.request.configuration.WallRequest
import com.spire.doc.Document

interface ReplacementService {

    fun replaceCabinTextInDocument(document: Document, cabin: CabinRequest)
    fun replaceWallTextInDocument(document: Document, wall: WallRequest)
    fun replaceHandrailTextInDocument(document: Document, handrail: HandrailRequest)
    fun replaceMirrorTextInDocument(document: Document, mirror: MirrorRequest)
    fun replaceDoorsTextInDocument(document: Document, doors: DoorsRequest)
    fun replaceBumpersTextInDocument(document: Document, bumpers: BumpersRequest)
    fun replaceCeilingTextInDocument(document: Document, ceiling: CeilingRequest)
    fun replaceFloorTextInDocument(document: Document, floor: FloorRequest)
    fun replaceControlPanelTextInDocument(document: Document, controlPanel: ControlPanelRequest)
}