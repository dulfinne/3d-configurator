package com.dulfinne.configurator.mapper

import com.dulfinne.configurator.dto.response.PaginatedResponse
import org.springframework.data.domain.Page

fun <T> Page<T>.toPaginatedResponse(): PaginatedResponse<T> {
    return PaginatedResponse(
        content = content,
        page = number,
        size = size,
        totalElements = totalElements,
        totalPages = totalPages
    )
}