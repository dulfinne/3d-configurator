package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.IconRequest
import com.dulfinne.configurator.dto.request.UpdateIconRequest
import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.entity.Icon

fun Icon.toResponse(): IconResponse = IconResponse(
    id = id,
    name = name,
    url = url,
    isDoor = isDoor,
    isWall = isWall,
    isFloor = isFloor,
    isCeiling = isCeiling,
    isCeilingMaterial = isCeilingMaterial,
    isControlPanel = isControlPanel,
    isHandrail = isHandrail,
    isBumper = isBumper,
    isIndicationBoard = isIndicationBoard,
    isFrame = isFrame
)

fun IconRequest.toEntity(): Icon = Icon(
    id = null,
    name = name,
    url = "",
    isDoor = isDoor,
    isWall = isWall,
    isFloor = isFloor,
    isCeiling = isCeiling,
    isCeilingMaterial = isCeilingMaterial,
    isControlPanel = isControlPanel,
    isHandrail = isHandrail,
    isBumper = isBumper,
    isIndicationBoard = isIndicationBoard,
    isFrame = isFrame
)

fun Icon.updateFromRequest(request: UpdateIconRequest) {
    this.name = request.name
    this.isDoor = request.isDoor
    this.isWall = request.isWall
    this.isFloor = request.isFloor
    this.isCeiling = request.isCeiling
    this.isCeilingMaterial = request.isCeilingMaterial
    this.isControlPanel = request.isControlPanel
    this.isHandrail = request.isHandrail
    this.isBumper = request.isBumper
    this.isIndicationBoard = request.isIndicationBoard
    this.isFrame = request.isFrame
}
