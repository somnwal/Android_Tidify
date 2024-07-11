import com.somnwal.app.common.setNamespace

plugins {
    alias(libs.plugins.somnwal.android.library)
    alias(libs.plugins.somnwal.android.hilt)

    id("kotlinx-serialization")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    setNamespace("core.datastore")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.datastoreProto)
    implementation(libs.androidx.datastore)

    implementation(libs.kotlinx.serialization.json)

}