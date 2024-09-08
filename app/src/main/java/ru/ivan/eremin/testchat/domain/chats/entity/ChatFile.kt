package ru.ivan.eremin.testchat.domain.chats.entity

import androidx.compose.runtime.Immutable
import java.util.Locale

@Immutable
sealed interface ChatFile {
    val id: String?
    val url: String?
    val needDownload: Boolean
        get() = url.isNullOrBlank()
    val isImage: Boolean
    val name: String?
    val fileType: FileType

    data class History(
        override val id: String,
        override val name: String,
        val size: Long,
        val signature: String,
        override val url: String?,
        override val fileType: FileType = FileType.getType(name.getFileType().orEmpty())
    ) : ChatFile {
        override val isImage: Boolean
            get() = fileType != FileType.OTHER
    }

    data class Session(
        override val id: String,
        override val name: String,
        val size: Long,
        val key: String,
        override val url: String?,
        override val fileType: FileType = FileType.getType(name.getFileType().orEmpty())
    ) : ChatFile {
        override val isImage: Boolean
            get() = fileType != FileType.OTHER
    }

    data class Url(override val url: String) : ChatFile {
        override val id: String?
            get() = null
        override val isImage: Boolean
            get() = true
        override val name: String
            get() = url
        override val fileType: FileType
            get() = FileType.OTHER
    }
}

private fun String.getFileType(): String? {
    val index = lastIndexOf(".")
    return if (isEmpty() || index == -1) {
        return null
    } else {
        substring(index + 1).lowercase(Locale.getDefault())
    }
}
