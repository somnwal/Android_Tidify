import com.somnwal.app.configureCoroutineAndroid
import com.somnwal.app.configureHiltAndroid
import com.somnwal.app.configureKotlinAndroid

// android library 모듈 프로젝트에 적용할 요소들

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureHiltAndroid()
configureCoroutineAndroid()