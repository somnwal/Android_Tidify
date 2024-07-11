import com.somnwal.app.common.setNamespace

plugins {
    alias(libs.plugins.somnwal.android.library)
    alias(libs.plugins.somnwal.android.hilt)

    id("kotlinx-serialization")
}

android {
    setNamespace("core.data")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.dataApi)
    implementation(projects.core.datastore)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    implementation(libs.kotlinx.serialization.json)
}