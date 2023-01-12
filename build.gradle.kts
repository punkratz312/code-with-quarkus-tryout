plugins {
//    id("com.avast.gradle.docker-compose") version "0.16.8"
    id("io.quarkus") version "2.15.3.Final"
//    kotlin("jvm")
    kotlin("plugin.allopen") version "1.7.22"
}
dependencies {
    implementation("com.google.firebase:firebase-admin:9.1.1")
    implementation("com.vladmihalcea:hibernate-types-52:2.21.1")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-orm-rest-data-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-logging-json")
    implementation("io.quarkus:quarkus-logging-sentry")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-opentelemetry-exporter-otlp")
    implementation("io.quarkus:quarkus-quartz")
    implementation("io.quarkus:quarkus-qute")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-resteasy-qute")
    implementation("io.quarkus:quarkus-smallrye-health")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("org.apache.maven:maven-artifact:4.0.0-alpha-2")
    implementation("org.jboss.logmanager:log4j-jboss-logmanager:1.3.0.Final")
    implementation("org.jboss.logmanager:log4j2-jboss-logmanager:1.1.1.Final")
    implementation("org.jboss.slf4j:slf4j-jboss-logmanager:1.2.0.Final")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.junit.platform:junit-platform-commons:1.9.1")
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:2.15.3.Final"))
    implementation(kotlin("stdlib"))
//    implementation(project(":dsl_implementation"))
//    implementation(project(":executor"))
//    implementation(project(":shared"))
    testImplementation("com.github.tomakehurst:wiremock-jre8:2.35.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-test-security-jwt")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}
allOpen {
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.ws.rs.Path")
}
tasks.withType<GenerateModuleMetadata> {
    suppressedValidationErrors.add("enforced-platform") // necessary for package build
}
repositories {
    jcenter()
    mavenCentral()
}
