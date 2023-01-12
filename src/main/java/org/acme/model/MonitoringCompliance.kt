package com.movisens.therapydesigner.executor.serverless.persistence.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import org.eclipse.microprofile.openapi.annotations.media.Schema

// @Entity
// class MonitoringCompliance : Monitoring() {
// TODO FIXME
data class MonitoringCompliance(
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, insertable = false, updatable = false, nullable = false)
    @Schema(readOnly = true)
    var id: UUID? = null,

    @Column(name = "value")
    var value: Int? = null
)