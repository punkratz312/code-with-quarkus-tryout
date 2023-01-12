package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.databind.JsonNode
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "participant_state")
@NamedQueries(
    NamedQuery(
        name = "ParticipantState.findByParticipant",
        query = """
                FROM participant_state ParticipantState
                WHERE ParticipantState.participantId = :participantId
        """
    )
)
data class ParticipantState(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    var id: UUID? = null,
    @Schema(readOnly = true)
    @Column(name = "participant_id", nullable = false)
    var participantId: UUID,
    @Type(type = "json")
    @Column(name = "value", nullable = false)
    var value: JsonNode,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()