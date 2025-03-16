package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.ProjectTemplate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProjectTemplateRepository : JpaRepository<ProjectTemplate, UUID> {
    fun findByName(name: String): ProjectTemplate?
    fun findAllByDesignProjectId(designProjectId: UUID, pageable: Pageable): Page<ProjectTemplate>
}