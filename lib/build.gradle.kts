plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.google.guava:guava:30.1.1-jre")
    testImplementation("io.kotest:kotest-assertions-core:5.3.1")
    testImplementation("io.kotest:kotest-framework-engine-jvm:5.3.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.3.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "10240m"
}