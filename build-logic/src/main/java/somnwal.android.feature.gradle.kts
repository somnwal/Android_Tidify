import com.somnwal.app.common.libs
import com.somnwal.app.configureHiltAndroid

plugins {
    id("somnwal.android.library")
    id("somnwal.android.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))

    val libs = project.extensions.libs

    implementation(libs.findLibrary("hilt.navigation.compose").get())
    implementation(libs.findLibrary("androidx.compose.navigation").get())

    implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
    implementation(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
}