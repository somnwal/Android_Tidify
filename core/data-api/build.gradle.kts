import com.somnwal.app.common.setNamespace

plugins {
    alias(libs.plugins.somnwal.android.library)
}

android {
    setNamespace("core.data.api")
}

dependencies {
    implementation(projects.core.model)
}