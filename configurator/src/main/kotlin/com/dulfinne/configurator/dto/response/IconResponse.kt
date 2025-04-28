package com.dulfinne.configurator.dto.response

import java.util.UUID

data class IconResponse(

    val id: UUID?,

    val name: String,

    val url: String,

    val isDoor: Boolean,

    val isWall: Boolean,

    val isFloor: Boolean,

    val isCeiling: Boolean,

    val isCeilingMaterial: Boolean,

    val isControlPanel: Boolean,

    val isHandrail: Boolean,

    val isBumper: Boolean,

    val isIndicationBoard: Boolean,

    val isFrame: Boolean,
)