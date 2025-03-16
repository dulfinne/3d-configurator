package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.DesignProject
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DesignProjectRepository : JpaRepository<DesignProject, UUID> {
    fun findByName(name: String): DesignProject?
}