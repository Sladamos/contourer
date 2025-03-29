plugins {
    id("java")
}

allprojects {
    group = "com.sladamos"
    version = "1.0"

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