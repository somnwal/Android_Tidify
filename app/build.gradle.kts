import com.somnwal.app.common.APPLICATION_ID
import com.somnwal.app.common.setNamespace

plugins {
    alias(libs.plugins.somnwal.android.application)
}

android {
    setNamespace("app")

    defaultConfig {
        applicationId = APPLICATION_ID
        versionCode = 1
        versionName = "1.0.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            //
        }
    }
}

dependencies {
    implementation(projects.feature.main)
}