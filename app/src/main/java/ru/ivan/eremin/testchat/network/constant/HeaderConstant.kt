package ru.ivan.eremin.testchat.network.constant

import android.os.Build

const val HEADER_USER_AGENT = "User-Agent"
const val HEADER_APP_VERSION = "App-Version"
const val HEADER_ATTEMPT_COUNT = "Attempt-Count"

object HeaderConstants {
    val HEADER_USER_AGENT_VALUE: String by lazy {
        "(Android ${Build.VERSION.SDK_INT})"
    }
}
