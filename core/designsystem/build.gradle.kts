import com.somnwal.app.common.setNamespace

plugins {
    alias(libs.plugins.somnwal.android.library)
    alias(libs.plugins.somnwal.android.compose)
}

android {
    setNamespace("core.designsystem")
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.androidx.appcompat)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
}