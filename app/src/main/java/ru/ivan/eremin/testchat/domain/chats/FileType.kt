package ru.ivan.eremin.testchat.domain.chats

enum class FileType(
    val suffix: List<String>,
    val needCompress: Boolean
) {
    JPEG(listOf("jpeg", "jpg"), true),
    SVG(listOf("svg"), false),
    WEPM(listOf("webp"), true),
    PNG(listOf("png"), true),
    GIF(listOf("gif"), false),
    HEIC(listOf("heic"), true),
    OTHER(emptyList(), true);

    companion object {
        fun getType(suffix: String): FileType {
            return entries.firstOrNull { suffix in it.suffix } ?: OTHER
        }
    }
}
