package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.request.IconRequest
import com.dulfinne.configurator.dto.request.UpdateIconRequest
import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.entity.Icon
import com.dulfinne.configurator.entity.enums.IconType

fun Icon.toResponse(): IconResponse = IconResponse(
    id = id,
    name = name,
    url = url,
    type = IconType.fromId(type),
)

fun IconRequest.toEntity(): Icon = Icon(
    id = null,
    name = name,
    url = "",
    type = type?.id ?: IconType.UNKNOWN.id,
)

fun Icon.updateFromRequest(request: UpdateIconRequest) {
    this.name = request.name
    this.type = request.type?.id ?: IconType.UNKNOWN.id
}