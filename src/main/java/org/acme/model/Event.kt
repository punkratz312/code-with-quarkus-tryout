package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.databind.JsonNode
import com.vladmihalcea.hibernate.type.json.JsonType
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

@TypeDef(name = "json", typeClass = JsonType::class)
@Entity(name = "event")
@NamedQueries(
    /**
     * As watermark is provided in millisecond precision and the createdAt timestamp in microseconds precision,
     * the createdAt timestamp must be truncated to milliseconds before comparison.
     * Additionally, watermark must be a double with the first 3 value after the comma representing the milliseconds.
     * Return oldest events first
     */
    NamedQuery(
        name = "Event.listAllLaterThenWatermark",
        query = """
                FROM event
                WHERE participant_id = :participantId
                AND date_trunc(created_at) > FUNCTION('to_timestamp',:watermark)
                ORDER BY created_at ASC
        """
    )
)
data class Event(
    @Id
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: UUID,
    @Column(name = "participant_id", nullable = false)
    var participantId: UUID,
    @Column(name = "payload")
    @Type(type = "json")
    var payload: JsonNode? = null,
    @Column(name = "source_type", nullable = false)
    var sourceType: String,
    @Column(name = "event_type", nullable = false)
    var eventType: String,
    @Column(name = "component_id_to_notify")
    var componentIdToNotify: UUID? = null,
    @Column(name = "source_id")
    var sourceId: UUID? = null,
    @Column(name = "belongs_to_id")
    var belongsToId: UUID? = null,
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    @Schema(readOnly = true)
    val createdAt: OffsetDateTime? = null
) : PanacheEntityBase()
