package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.Icon
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable

interface IconRepository : JpaRepository<Icon, Long> {
    fun findAllByType(type: Int, pageable: Pageable): Page<Icon>
    fun findByName(name: String): Icon?
}