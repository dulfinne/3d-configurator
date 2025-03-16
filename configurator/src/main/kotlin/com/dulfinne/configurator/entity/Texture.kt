package com.dulfinne.configurator.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "textures")
class Texture(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID?,

    @Column(name = "name", unique = true, nullable = false)
    var name: String,

    @Column(name = "base_color", nullable = false)
    var baseColor: String,

    @Column(name = "base_texture_url")
    var baseTextureUrl: String?,

    @Column(name = "alpha_map_url")
    var alphaMapUrl: String?,

    @Column(name = "bump_map_url")
    var bumpMapUrl: String?,

    @Column(name = "normal_map_url")
    var normalMapUrl: String?,

    @Column(name = "metalness_map_url")
    var metalnessMapUrl: String?,

    @Column(name = "roughness_map_url")
    var roughnessMapUrl: String?,

    @Column(name = "ao_map_url")
    var aoMapUrl: String?,

    @Column(name = "displacement_map_url")
    var displacementMapUrl: String?,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "properties_id", referencedColumnName = "id", nullable = false)
    var properties: TextureProperties,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "icon_id", referencedColumnName = "id", nullable = false)
    var icon: Icon
)