package com.dulfinne.configurator.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "design_projects")
class DesignProject(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID?,

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "designProject", cascade = [(CascadeType.ALL)])
    var templates: MutableList<ProjectTemplate> = mutableListOf()
)