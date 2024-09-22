package ru.ivan.eremin.chat.entity.http

data class HttpRequest (
    val host: String,
    val url: String,
    val query: String,
    val method: String,
    val headers: Map<String, List<String>>,
    val data: String?
)