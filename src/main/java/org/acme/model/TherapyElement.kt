package com.movisens.therapydesigner.executor.serverless.persistence.model

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp

@Entity(name = "therapy_element")
@NamedQueries(
    NamedQuery(
        name = "TherapyElement.findByTherapyDesign",
        query = """
                SELECT TherapyElement
                FROM therapy_element TherapyElement, therapy_design TherapyDesign
                WHERE TherapyElement.therapyDesignId = TherapyDesign.id
                AND TherapyDesign.id = :therapyDesignId
        """
    )
)
data class TherapyElement(
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    var id: UUID,
    @Column(name = "therapy_design_id", nullable = false)
    val therapyDesignId: UUID,
    @OneToMany(mappedBy = "therapyElementId", cascade = [ALL], fetch = EAGER)
    var therapyVariables: MutableSet<TherapyVariable> = mutableSetOf(),
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "title", nullable = false)
    var title: String,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", nullable = false)
    var description: String,
    @Column(name = "\"order\"", nullable = false)
    var order: Short,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()