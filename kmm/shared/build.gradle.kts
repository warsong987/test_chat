import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    val xcframeworkName = "Shared"
    val xcf = XCFramework(xcframeworkName)
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {

            binaryOption("bundleId", "ru.ivan.eremin.$xcframeworkName")
            baseName = xcframeworkName
            isStatic = true
            xcf.add(this)
        }
    }

    targets.withType<KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions { freeCompilerArgs.add("-Xexport-kdoc") }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(project(":kmm:utils:platform"))
            implementation(project(":kmm:core:network"))
            implementation(project(":kmm:feature:auth:impl"))
            api(project(":kmm:feature:auth:api"))
            implementation(project(":kmm:feature:registration:impl"))
            api(project(":kmm:feature:registration:api"))
            implementation(project(":kmm:feature:profile:impl"))
            api(project(":kmm:feature:profile:api"))
            implementation(project(":kmm:feature:chat:impl"))
            api(project(":kmm:feature:chat:api"))
        }
    }
}

android {
    namespace = "ru.ivan.eremin.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}