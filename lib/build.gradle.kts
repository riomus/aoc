plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("io.kotest.multiplatform") version "5.0.2"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.google.guava:guava:30.1.1-jre")
    testImplementation("io.kotest:kotest-assertions-core:5.0.1")
    testImplementation("io.kotest:kotest-framework-engine-jvm:5.0.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}