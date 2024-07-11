package com.somnwal.app

import com.somnwal.app.common.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureRoomAndroid() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.kapt")
    }

    val libs = extensions.libs

    dependencies {
        "implementation"(libs.findLibrary("room.runtime").get())
        "implementation"(libs.findLibrary("room.ktx").get())
        "annotationProcessor"(libs.findLibrary("room.compiler").get())

        "kapt"(libs.findLibrary("room.compiler").get())
    }
}

internal class RoomAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        // 해당 타겟 프로젝트들에게 Room을 적용한다.
        with(target) {
            configureRoomAndroid()
        }
    }
}