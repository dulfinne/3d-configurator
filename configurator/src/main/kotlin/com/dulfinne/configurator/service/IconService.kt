package com.dulfinne.configurator.service

import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse

interface IconService {
    fun getAllIcons(page: Int, size: Int): PaginatedResponse<IconResponse>
}