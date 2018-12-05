import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "net.sierisimo"
version = "0.0.1"

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.3.10"))
    }
}

plugins {
    kotlin("jvm") version "1.3.10"
    id("io.gitlab.arturbosch.detekt") version "1.0.0-RC11"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    val junitJupiterVersion = "5.3.1"

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")

    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    testImplementation("io.mockk:mockk:1.8.13")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.10")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

//val test by tasks.getting(Test::class) {
tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
    }

    reports {
        html.isEnabled = true
    }
}

detekt {
    toolVersion = "1.0.0-RC11"
    input = files("src/main/kotlin")
    filters = ".*/resources/.*,.*/build/.*"
    config = files("default-detekt-config.yml")

    reports {
        xml {
            enabled = true
        }
        html {
            enabled = true                                // Enable/Disable HTML report (default: true)
        }
    }
}