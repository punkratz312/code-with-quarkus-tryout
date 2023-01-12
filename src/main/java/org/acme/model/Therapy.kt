package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.movisens.therapydesigner.executor.serverless.service.CleanupService.Companion.DELETE_DELAY_DAYS
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "therapy")
@NamedQueries(
    NamedQuery(
        name = "Therapy.findByParticipant",
        query = """
                SELECT Therapy
                FROM therapy Therapy, participant Participant
                WHERE Therapy.id = Participant.therapyId
                AND Participant.id = :participantId
        """
    ),
    NamedQuery(
        name = "Therapy.findAllExpired",
        query = """
                FROM therapy Therapy
                WHERE Therapy.deletedAt <= CURRENT_DATE - $DELETE_DELAY_DAYS
        """
    )
)
data class Therapy(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: UUID? = null,
    @Column(name = "therapy_design_id", nullable = false)
    var therapyDesignId: UUID,
    @OneToMany(mappedBy = "therapyId", fetch = EAGER)
    var participants: MutableSet<Participant> = mutableSetOf(),
    @JsonIgnore
    @OneToMany(mappedBy = "therapyId")
    var informedConsents: MutableSet<InformedConsent> = mutableSetOf(),
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null,
    @Column(name = "deleted_at", updatable = true)
    var deletedAt: OffsetDateTime? = null
) : PanacheEntityBase()