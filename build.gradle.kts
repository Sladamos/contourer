plugins {
    id("java")
}

group = "com.sladamos"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("java")
    }

    tasks.test {
        useJUnitPlatform()
    }
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.assertj.core)
}