package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.databind.JsonNode
import com.movisens.therapydesigner.executor.dsl.impl.variables.EditorType
import com.movisens.therapydesigner.executor.dsl.impl.variables.Status
import com.movisens.therapydesigner.executor.serverless.exception.ConstraintViolationException
import com.vladmihalcea.hibernate.type.json.JsonType
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.io.Serializable
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.NamedNativeQueries
import javax.persistence.NamedNativeQuery
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.PrePersist
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef

@Entity(name = "therapy_variable_value")
@TypeDef(name = "json", typeClass = JsonType::class)
@IdClass(TherapyVariableValueKey::class)
@NamedQueries(
    NamedQuery(
        name = "TherapyVariableValue.findOneLatestValue",
        query = """
                FROM therapy_variable_value
                WHERE participant_id = :participantId
                AND therapy_variable_id = :therapyVariableId
                ORDER BY created_at DESC
        """
    ),
    NamedQuery(
        name = "TherapyVariableValue.findManyHistoryValuesWithin",
        query = """
                FROM therapy_variable_value
                WHERE participant_id = :participantId
                AND therapy_variable_id IN :variables
                AND created_at >= :start
                AND created_at <= :end
                ORDER BY created_at DESC
        """
    ),
    NamedQuery(
        name = "TherapyVariableValue.findOneHistoryValues",
        query = """
                FROM therapy_variable_value
                WHERE participant_id = :participantId
                AND therapy_variable_id = :therapyVariableId
                ORDER BY created_at ASC
        """
    )
)
@NamedNativeQueries(
    NamedNativeQuery(
        name = "TherapyVariableValue.findAllLatestValues",
        query = """
                SELECT DISTINCT ON (therapy_variable_id) therapy_variable_id, participant_id, editor_id, editor_type, variable_value, created_at, activated_Interaction_id, missing
                FROM therapy_variable_value
                WHERE participant_id = :participantId
                ORDER BY therapy_variable_id, created_at DESC
        """,
        resultClass = TherapyVariableValue::class
    ),
    NamedNativeQuery(
        name = "TherapyVariableValue.findManyLatestValues",
        query = """
                SELECT DISTINCT ON (therapy_variable_id) therapy_variable_id, participant_id, editor_id, editor_type, variable_value, created_at, activated_Interaction_id, missing
                FROM therapy_variable_value
                WHERE participant_id = :participantId
                AND therapy_variable_id IN :variables
                ORDER BY therapy_variable_id, created_at DESC
        """,
        resultClass = TherapyVariableValue::class
    )
)
class TherapyVariableValue private constructor(
    @Type(type = "json")
    @Column(name = "variable_value")
    val variableValue: JsonNode?,
    @Column(name = "missing")
    val missing: Status? = null
) : Serializable, PanacheEntityBase() {

    @Id
    @Column(name = "therapy_variable_id", nullable = false)
    @Schema(readOnly = true)
    lateinit var therapyVariableId: UUID

    @Id
    @Column(name = "participant_id", nullable = false)
    @Schema(readOnly = true)
    lateinit var participantId: UUID

    @Column(name = "activated_Interaction_id", nullable = false)
    lateinit var activatedInteraction: UUID

    @Column(name = "editor_id", nullable = false)
    lateinit var editorId: UUID

    @Column(name = "editor_type", nullable = false)
    lateinit var editorType: EditorType

    @Id
    @Column(name = "created_at", updatable = false, nullable = false)
    @Schema(readOnly = true)
    lateinit var createdAt: OffsetDateTime

    constructor(
        therapyVariableId: UUID,
        participantId: UUID,
        activatedInteraction: UUID,
        editorId: UUID,
        editorType: EditorType,
        variableValue: JsonNode?,
        missing: Status? = null
    ) : this(variableValue, missing) {
        if (variableValue == null && missing == null) {
            throw ConstraintViolationException(
                "VariableValue and missing property cannot both be null at the same time. Variable id = $therapyVariableId" // ktlint-disable max-line-length
            )
        }

        this.therapyVariableId = therapyVariableId
        this.participantId = participantId
        this.activatedInteraction = activatedInteraction
        this.editorId = editorId
        this.editorType = editorType
    }

    @PrePersist
    fun prePersist() {
        createdAt = OffsetDateTime.now((ZoneId.of("UTC")))
    }

    fun copy(
        therapyVariableId: UUID = this.therapyVariableId,
        participantId: UUID = this.participantId,
        activatedInteraction: UUID = this.activatedInteraction,
        editorId: UUID = this.editorId,
        editorType: EditorType = this.editorType,
        variableValue: JsonNode? = this.variableValue,
        missing: Status? = this.missing,
        createdAt: OffsetDateTime = this.createdAt
    ): TherapyVariableValue {
        return TherapyVariableValue(
            therapyVariableId,
            participantId,
            activatedInteraction,
            editorId,
            editorType,
            variableValue,
            missing
        ).apply { this.createdAt = createdAt }
    }

    override fun hashCode(): Int {
        return Objects.hash(therapyVariableId, participantId, createdAt, variableValue)
    }

    override fun equals(other: Any?): Boolean {
        return when {
            (this === other) -> true
            (other !is TherapyVariableValue) -> false
            else ->
                this.therapyVariableId == other.therapyVariableId &&
                    this.participantId == other.participantId &&
                    if (this::createdAt.isInitialized && other::createdAt.isInitialized) {
                        this.createdAt == other.createdAt
                    } else {
                        !this::createdAt.isInitialized && !other::createdAt.isInitialized
                    } &&
                    this.variableValue == other.variableValue
        }
    }
}

private class TherapyVariableValueKey : Serializable {
    lateinit var therapyVariableId: UUID
    lateinit var participantId: UUID
    lateinit var createdAt: OffsetDateTime

    override fun hashCode(): Int {
        return Objects.hash(therapyVariableId, participantId, createdAt)
    }

    override fun equals(other: Any?): Boolean {
        return when {
            (this === other) -> true
            (other !is TherapyVariableValueKey) -> false
            else ->
                this.therapyVariableId == other.therapyVariableId &&
                    this.participantId == other.participantId &&
                    this.createdAt == other.createdAt
        }
    }
}