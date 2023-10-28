@Suppress("DSL_SCOPE_VIOLATION") 
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.koltin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.golnegari.feature.moviedetail"

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
    testImplementation(libs.testing.junit)
    androidTestImplementation(libs.testing.junit.ext)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
}