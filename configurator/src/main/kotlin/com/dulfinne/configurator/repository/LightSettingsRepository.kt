package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.LightSettings
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LightSettingsRepository : JpaRepository<LightSettings, Long> {
}