package ru.ivan.eremin.testchat.domain.chats.entity

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class ChatSelectedFile(
    val uri: Uri,
    val id: String = uri.toString()
)
