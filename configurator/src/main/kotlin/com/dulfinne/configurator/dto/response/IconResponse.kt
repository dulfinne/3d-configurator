package com.dulfinne.configurator.dto.response

import com.dulfinne.configurator.entity.enums.IconType
import java.util.UUID

data class IconResponse(

    val id: UUID?,

    val name: String,

    val url: String,

    val type: IconType,
)