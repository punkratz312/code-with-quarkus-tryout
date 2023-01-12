package org.acme

import com.movisens.therapydesigner.executor.serverless.persistence.model.TherapyElement
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource
import io.quarkus.rest.data.panache.ResourceProperties
import java.util.UUID
import javax.transaction.Transactional

@Transactional
@ResourceProperties(path = "therapy-element", rolesAllowed = ["ADMIN"], exposed = false)
interface TherapyElementResource : PanacheEntityResource<TherapyElement, UUID>
