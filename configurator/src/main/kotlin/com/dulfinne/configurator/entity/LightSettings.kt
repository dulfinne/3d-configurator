package com.dulfinne.configurator.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "light_settings")
class LightSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long?,

    @Column(name = "react_intensity", nullable = false)
    var reactIntensity: Double,

    @Column(name = "react_color", nullable = false)
    var reactColor: String,

    @Column(name = "ambient_intensity", nullable = false)
    var ambientIntensity: Double,

    @Column(name = "ambient_color", nullable = false)
    var ambientColor: String,
)