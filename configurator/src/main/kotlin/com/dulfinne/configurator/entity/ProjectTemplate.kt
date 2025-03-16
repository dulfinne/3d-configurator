package com.dulfinne.configurator.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "project_templates")
class ProjectTemplate(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID?,

    @ManyToOne
    @JoinColumn(name = "design_project_id", referencedColumnName = "id")
    var designProject: DesignProject?,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "configuration", nullable = false)
    var configuration: String,

    @Column(name = "preview_image_url", nullable = false)
    var previewImageUrl: String
)
