package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.entity.enums.IconType

interface IconService {
    fun getAllIcons(page: Int, size: Int): PaginatedResponse<IconResponse>
    fun getAllIconsByType(type: IconType, page: Int, size: Int): PaginatedResponse<IconResponse>
}