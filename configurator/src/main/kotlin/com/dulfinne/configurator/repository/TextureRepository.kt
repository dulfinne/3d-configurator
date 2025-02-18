package com.dulfinne.configurator.repository

import com.dulfinne.configurator.entity.Texture
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TextureRepository : JpaRepository<Texture, UUID> {

    @Query(
        """SELECT t FROM Texture t 
       LEFT JOIN FETCH t.properties""")
    fun findAllWithProperties(pageable: PageRequest): Page<Texture>

    fun findByName(name: String): Texture?
}