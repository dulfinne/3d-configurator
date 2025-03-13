package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.Texture
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TextureRepository : JpaRepository<Texture, UUID> {

    @Query(
        """SELECT t FROM Texture t 
       LEFT JOIN FETCH t.properties
       LEFT JOIN FETCH t.icon"""
    )
    fun findAllWithProperties(pageable: PageRequest): Page<Texture>

    @Query(
        """SELECT t FROM Texture t 
       LEFT JOIN FETCH t.properties
       LEFT JOIN FETCH t.icon"""
    )
    fun findAllWithIcons(): List<Texture>

    fun findByName(name: String): Texture?
    fun findByIconId(id: UUID): Texture?
}