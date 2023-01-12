package org.acme

import com.movisens.therapydesigner.executor.serverless.persistence.model.TherapyDesign
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource
import io.quarkus.rest.data.panache.MethodProperties
import io.quarkus.rest.data.panache.ResourceProperties
import java.util.UUID
import javax.transaction.Transactional

@ResourceProperties(path = "therapy-design", rolesAllowed = ["ADMIN"])
@Transactional
interface TherapyDesignEntityResource : PanacheEntityResource<TherapyDesign, UUID> {

    @MethodProperties(exposed = false)
    override fun delete(id: UUID?): Boolean
}
