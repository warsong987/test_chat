package ru.ivan.eremin.testchat.app.config

import android.content.Context

interface AppConfiguration {
    val context: Context
    val versionCode: Int
    val versionName: String
    val applicationId: String
    val userAgent: String
}