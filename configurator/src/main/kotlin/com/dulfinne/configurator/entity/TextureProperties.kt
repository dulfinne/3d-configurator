package com.dulfinne.configurator.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "texture_properties")
class TextureProperties(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID?,

    @Column(name = "bump_scale")
    var bumpScale: Double?,

    @Column(name = "metalness", nullable = false)
    var metalness: Double,

    @Column(name = "roughness", nullable = false)
    var roughness: Double,

    @Column(name = "emissive_intensity")
    var emissiveIntensity: Double?,

    @Column(name = "tile_size_x")
    var tileSizeX: Double?,

    @Column(name = "tile_size_y")
    var tileSizeY: Double?,
)