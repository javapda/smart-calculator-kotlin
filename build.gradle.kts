import java.util.*

plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
//    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
}
val mainKotlinClass = "com.javapda.smartcalculator.SmartCalculatorKt"
val theJdkVersion = 17
val junitVersion = "5.9.3"

group = "com.javapda"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.23")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.moshi:moshi-adapters:1.15.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.1")
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(theJdkVersion)
}

// Make the build task depend on shadowJar
tasks.named("build") {
    dependsOn("shadowJar")
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = mainKotlinClass
        attributes["Jdk-Version"] = theJdkVersion
        attributes["Author"] = "John Kroubalkian"
        attributes["Build-Date"] = Date()
    }
}
