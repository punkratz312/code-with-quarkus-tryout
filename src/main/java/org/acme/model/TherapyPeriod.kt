package com.movisens.therapydesigner.executor.serverless.persistence.model

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "therapy_period")
data class TherapyPeriod(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: UUID? = null,
    @Column(name = "participant_id", nullable = false)
    var participantId: UUID,
    @Column(name = "start")
    var start: LocalDateTime? = null,
    @Column(name = "\"end\"")
    var end: LocalDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()