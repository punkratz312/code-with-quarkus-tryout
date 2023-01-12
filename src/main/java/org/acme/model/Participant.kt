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
import javax.persistence.Lob
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "participant")
@NamedQueries(
    NamedQuery(
        name = "Participant.findByCoupleSecret",
        query = """
                FROM participant
                WHERE couple_secret = :coupleSecret
        """
    ),
    NamedQuery(
        name = "Participant.findAllExpired",
        query = """
                FROM participant
                WHERE deleted_at <= CURRENT_DATE - $DELETE_DELAY_DAYS
        """
    )
)
data class Participant(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: UUID? = null,
    @Column(name = "therapy_id", nullable = false)
    val therapyId: UUID,
    @OneToMany(mappedBy = "participantId", fetch = EAGER)
    var therapyPeriods: MutableSet<TherapyPeriod> = mutableSetOf(),
    @JsonIgnore
    @OneToMany(mappedBy = "participantId")
    var events: MutableSet<Event> = mutableSetOf(),
    @OneToMany(mappedBy = "participantId", fetch = EAGER)
    var monitorings: MutableSet<Monitoring> = mutableSetOf(),
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "push_token")
    val pushToken: String? = null,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "couple_secret")
    var coupleSecret: String? = null,
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