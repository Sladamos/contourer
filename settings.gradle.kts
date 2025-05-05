enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

file(".").listFiles()?.forEach { module ->
    if (module.isDirectory && file("${module.absolutePath}/build.gradle.kts").exists()) {
        include(":${module.name}")
    }
}
