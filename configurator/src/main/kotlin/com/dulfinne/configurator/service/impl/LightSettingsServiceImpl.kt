package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.dto.request.LightSettingsRequest
import com.dulfinne.configurator.dto.response.LightSettingsResponse
import com.dulfinne.configurator.entity.LightSettings
import com.dulfinne.configurator.mapper.toEntity
import com.dulfinne.configurator.mapper.toResponse
import com.dulfinne.configurator.mapper.updateFromRequest
import com.dulfinne.configurator.repository.LightSettingsRepository
import com.dulfinne.configurator.service.LightSettingsService
import com.dulfinne.configurator.util.ExceptionMessages
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LightSettingsServiceImpl(val repository: LightSettingsRepository) : LightSettingsService {
    override fun getLightSettings(): LightSettingsResponse {
        val settings = getLightSettingsIfExists(1L)
        return settings.toResponse()
    }

    override fun saveLightSettings(request: LightSettingsRequest): LightSettingsResponse {
        repository.findByIdOrNull(1L)?.let {
            throw EntityExistsException(ExceptionMessages.LIGHT_SETTINGS_EXISTS)
        }

        val settings = request.toEntity()
        repository.save(settings)
        return settings.toResponse()
    }

    override fun updateLightSettings(request: LightSettingsRequest): LightSettingsResponse {
        val settings = getLightSettingsIfExists(1L)
        settings.updateFromRequest(request)
        repository.save(settings)
        return settings.toResponse()
    }

    private fun getLightSettingsIfExists(id: Long): LightSettings {
        return repository.findByIdOrNull(id)
            ?: throw EntityNotFoundException(ExceptionMessages.LIGHT_SETTINGS_NOT_FOUND.format(id))
    }
}