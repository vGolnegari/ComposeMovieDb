@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.koltin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.golnegari.core.repository"
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
    testImplementation(libs.testing.junit)
    androidTestImplementation(libs.testing.junit.ext)
}