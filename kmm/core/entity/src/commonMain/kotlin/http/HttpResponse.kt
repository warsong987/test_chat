package http

data class HttpResponse(
    val code: Int,
    val headers: Map<String, List<String>>,
    val data: String?,
)
