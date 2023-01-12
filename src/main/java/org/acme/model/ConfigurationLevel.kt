package com.movisens.therapydesigner.executor.serverless.persistence.model

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import org.eclipse.microprofile.openapi.annotations.media.Schema

@Entity(name = "configuration_level")
data class ConfigurationLevel(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: Long? = null,
    @Column(name = "group_edit", nullable = false)
    var groupEdit: Boolean = false,
    @Column(name = "therapist_edit", nullable = false)
    var therapistEdit: Boolean = false,
    @Column(name = "therapist_view", nullable = false)
    var therapistView: Boolean = false,
    @Column(name = "participant_edit", nullable = false)
    var participantEdit: Boolean = false,
    @Column(name = "participant_view", nullable = false)
    var participantView: Boolean = false
) : PanacheEntityBase()