dependencies {
    annotationProcessor(libs.lombok)

    implementation(libs.lombok)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.assertj.core)
}