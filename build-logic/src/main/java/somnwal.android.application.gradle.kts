import com.somnwal.app.configureHiltAndroid
import com.somnwal.app.configureKotlinAndroid

// Application 전반적으로 적용됨
plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()