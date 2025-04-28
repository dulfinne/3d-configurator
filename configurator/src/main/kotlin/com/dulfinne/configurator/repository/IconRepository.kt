package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.Icon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IconRepository : JpaRepository<Icon, Long> {
    fun findByName(name: String): Icon?
}