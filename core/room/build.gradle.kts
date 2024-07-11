import com.somnwal.app.common.setNamespace

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.somnwal.android.library)
    alias(libs.plugins.somnwal.android.room)

    id("kotlinx-serialization")
}

android {
    setNamespace("core.room")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
