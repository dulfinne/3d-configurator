package com.dulfinne.configurator.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "icons")
class Icon(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID?,

    @Column(name = "name", unique = true, nullable = false)
    var name: String,

    @Column(name = "url", nullable = false)
    var url: String,

    @Column(name = "is_door", nullable = false)
    var isDoor: Boolean = false,

    @Column(name = "is_wall", nullable = false)
    var isWall: Boolean = false,

    @Column(name = "is_floor", nullable = false)
    var isFloor: Boolean = false,

    @Column(name = "is_ceiling", nullable = false)
    var isCeiling: Boolean = false,

    @Column(name = "is_ceiling_material", nullable = false)
    var isCeilingMaterial: Boolean = false,

    @Column(name = "is_control_panel", nullable = false)
    var isControlPanel: Boolean = false,

    @Column(name = "is_handrail", nullable = false)
    var isHandrail: Boolean = false,

    @Column(name = "is_bumper", nullable = false)
    var isBumper: Boolean = false,

    @Column(name = "is_indication_board", nullable = false)
    var isIndicationBoard: Boolean = false,

    @Column(name = "is_frame", nullable = false)
    var isFrame: Boolean = false
)