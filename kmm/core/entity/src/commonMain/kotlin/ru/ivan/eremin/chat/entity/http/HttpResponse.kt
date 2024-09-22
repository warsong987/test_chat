package ru.ivan.eremin.chat.entity.http

data class HttpResponse(
    val code: Int,
    val headers: Map<String, List<String>>,
    val data: String?,
)
