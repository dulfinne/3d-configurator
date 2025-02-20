package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.response.IconResponse
import com.dulfinne.configurator.dto.response.PaginatedResponse
import com.dulfinne.configurator.entity.IconType
import com.dulfinne.configurator.mapper.toPaginatedResponse
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.repository.IconRepository
import com.dulfinne.configurator.service.IconService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IconServiceImpl(val iconRepository: IconRepository) : IconService {
    @Transactional(readOnly = true)
    override fun getAllIcons(page: Int, size: Int): PaginatedResponse<IconResponse> {
        val icons = iconRepository.findAll(PageRequest.of(page, size))
        return icons.map { it.toResponse() }.toPaginatedResponse()
    }

    @Transactional(readOnly = true)
    override fun getAllIconsByType(type: IconType, page: Int, size: Int): PaginatedResponse<IconResponse> {
        val icons = iconRepository.findAllByType(type.id, PageRequest.of(page, size))
        return icons.map { it.toResponse() }.toPaginatedResponse()
    }
}