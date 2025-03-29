file("libs").listFiles()?.forEach { module ->
    if (module.isDirectory && file("${module.absolutePath}/build.gradle.kts").exists()) {
        include(":libs:${module.name}")
    }
}
