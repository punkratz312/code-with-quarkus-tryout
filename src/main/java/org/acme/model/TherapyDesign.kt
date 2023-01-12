package com.movisens.therapydesigner.executor.serverless.persistence.model

import com.fasterxml.jackson.annotation.JsonIgnore
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

@Entity(name = "therapy_design")
@NamedQueries(
    NamedQuery(
        name = "TherapyDesign.findByTherapy",
        query = """
                SELECT TherapyDesign
                FROM therapy_design TherapyDesign, therapy Therapy
                WHERE TherapyDesign.id = Therapy.therapyDesignId
                AND Therapy.id = :therapyId
        """
    )
)
data class TherapyDesign(
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    var id: UUID,
    @OneToMany(mappedBy = "therapyDesignId", cascade = [ALL], fetch = EAGER)
    var therapyElements: MutableSet<TherapyElement> = mutableSetOf(),
    @JsonIgnore
    @OneToMany(mappedBy = "therapyDesignId")
    var therapies: MutableSet<Therapy> = mutableSetOf(),
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "title", nullable = false)
    var title: String,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description", nullable = false)
    var description: String,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "version", nullable = false)
    var version: String,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()