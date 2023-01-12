package com.movisens.therapydesigner.executor.serverless.persistence.model

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.OffsetDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob

@Entity(name = "informed_consent")
data class InformedConsent(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    var id: UUID? = null,
    @Column(name = "therapy_id", nullable = false)
    var therapyId: UUID,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "language", nullable = false)
    var language: String,
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content", nullable = false)
    var content: String,
    @Column(name = "signing_required", nullable = false)
    var signingRequired: Boolean,
    @Schema(readOnly = true)
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    val createdAt: OffsetDateTime? = null,
    @Schema(readOnly = true)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: OffsetDateTime? = null
) : PanacheEntityBase()
