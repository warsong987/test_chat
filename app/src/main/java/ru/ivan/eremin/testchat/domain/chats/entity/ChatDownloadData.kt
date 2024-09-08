package ru.ivan.eremin.testchat.domain.chats.entity

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.Job

@Immutable
data class ChatDownloadData(
    val chatFile: ChatFile,
    val exception: Exception? = null,
    val job: Job? = null
) {
    val state
        get() = when {
            !chatFile.needDownload -> DownloadState.SUCCESS
            job?.isActive == true -> DownloadState.PROGRESS
            exception != null -> DownloadState.ERROR
            else -> DownloadState.NOT_STARTED
        }
}
