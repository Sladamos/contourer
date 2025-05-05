plugins {
    id("org.openjfx.javafxplugin") version "0.0.13"
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    annotationProcessor(libs.lombok)

    implementation(libs.bootstrapfx.core)
    implementation(libs.lombok)
    implementation(projects.contourerCore)
}