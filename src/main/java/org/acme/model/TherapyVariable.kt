package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.databind.JsonNode
import com.movisens.therapydesigner.executor.dsl.models.VariableType
import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "therapy_variable")
@NamedQueries(
    NamedQuery(
        name = "TherapyVariable.findAllByTherapyElement",
        query = """
                SELECT TherapyVariable
                FROM therapy_variable TherapyVariable, therapy_element TherapyElement
                WHERE TherapyVariable.therapyElementId = TherapyElement.id
                AND TherapyElement.id = :therapyElementId
        """
    ),
    NamedQuery(
        name = "TherapyVariable.findAllWithinTimeRange",
        query = """
                FROM therapy_variable TherapyVariable
                WHERE TherapyVariable.createdAt >= :start
                AND TherapyVariable.updatedAt <= :end
        """
    )
)
data class TherapyVariable(
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    var id: UUID,
    @Column(name = "therapy_element_id", nullable = false)
    val therapyElementId: UUID,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "display_name", nullable = false)
    var displayName: String,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", nullable = false)
    var description: String,
    @Column(name = "reset_at_midnight", nullable = false)
    var resetAtMidnight: Boolean = false,
    @Column(name = "type", nullable = false)
    var type: VariableType,
    @ManyToOne(optional = false, cascade = [ALL])
    @JoinColumn(name = "configuration_level_id", nullable = false)
    var configurationLevel: ConfigurationLevel,
    @Type(type = "json")
    @Column(name = "default_value", nullable = false)
    var defaultValue: JsonNode,
    @Column(name = "\"order\"", nullable = false)
    var order: Short,
    @Column(name = "mandatory", nullable = false)
    var mandatory: Boolean = false,
    @Type(type = "json")
    @Column(name = "validator_props")
    var validatorProps: JsonNode? = null,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()