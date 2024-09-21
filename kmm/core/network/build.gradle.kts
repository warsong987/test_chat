@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
}

kotlin {

    androidTarget{
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negatiation)
            implementation(libs.ktor.json)
            implementation(libs.ktor.cache)
            implementation(libs.ktor.encoding)
            implementation(libs.koin.core)
            api(libs.kotlinx.serialization.json)
            implementation(project(":kmm:core:config"))
            implementation(project(":kmm:core:entity"))
            implementation(project(":kmm:utils:platform"))
            implementation(project(":kmm:utils:kotlin"))
        }
    }
}

android {
    namespace = "ru.ivan.eremin.chat.network"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}