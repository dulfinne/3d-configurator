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

    @Column(name = "type", nullable = false)
    var type: Int,
)