@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.koltin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.golnegari.core.testing"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:repository"))
    api(libs.hilt.android.testing)
    api(libs.hilt.android.testing)
    api(libs.junit)
    implementation(libs.pagingCommon)
    api(libs.hilt.android.testing)
    api(libs.kotlinx.coroutines.test)
    implementation(libs.hilt.android)
    testImplementation(libs.pagingCommon)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.ext.compiler)
    kaptTest(libs.hilt.compiler)
}