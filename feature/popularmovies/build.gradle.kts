@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.koltin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.golnegari.feature.popularmovies"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":base"))
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.feature.compose)
    implementation(libs.composePaging)
    testImplementation(libs.testing.junit)
    androidTestImplementation(libs.testing.junit.ext)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
}
