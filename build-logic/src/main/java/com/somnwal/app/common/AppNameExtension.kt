package com.somnwal.app.common

import org.gradle.api.Project

const val PROJECT_NAME = "com.somnwal.budgetmap"
const val APPLICATION_ID = "com.somnwal.budgetmap.app"

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "$PROJECT_NAME.$name"
    }
}